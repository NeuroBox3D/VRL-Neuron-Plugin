/**
 * VRL-MembranePotentialMapping plugin
 *
 * @date 2014-18-06
 * @author sgrein
 * @see mbreit's implementation was adapted, thanks.
*
 */
// package name
package edu.gcsc.vrl.MembranePotentialMapping;

// imports
import edu.gcsc.vrl.ug.api.*;
import edu.gcsc.vrl.userdata.FunctionDefinition;
import edu.gcsc.vrl.userdata.UserDataTuple;
import edu.gcsc.vrl.userdata.UserDependentSubsetModel;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamGroupInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @brief configuration of the simulation setup
 * @author stephan
 */
@ComponentInfo(name = "MembranePotentialMapping", category = "/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class MembranePotentialMapping implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @param gridFile
	 * @param num_refs
	 * @param num_prerefs
	 * @param problemDefinition
	 * @param diffusionData
	 * @param hocGeometry
	 * @param hocStim
	 * @param vdccData
	 * @param vdccChannelType
	 * @param bndData
	 * @param startValue
	 * @return
	 */
	@MethodInfo(valueStyle = "multi-out", interactive = false)
	@OutputInfo(
		style = "multi-out",
		elemNames = {"Domain Disc", "VDCC Disc", "Approximation Space", "Initial Solution", "NEURON Setup"},
		elemTypes = {I_DomainDiscretization.class, I_OneSidedBorgGrahamFV1WithVM2UGNEURON.class, I_ApproximationSpace.class, UserDataTuple[].class, I_Transformator.class}
	)
	public Object[] invoke(
		@ParamGroupInfo(group = "Domain")
		@ParamInfo(name = "Grid", style = "ugx-load-dialog", options = "ugx_tag=\"gridFile\"") java.io.File gridFile,
		/// SIMULATION PARAMS ///
		// Refiner params
		@ParamGroupInfo(group = "Simulation params|false;Refiner")
		@ParamInfo(name = "refinements", style = "default", options = "value=0") Integer num_refs,
		@ParamGroupInfo(group = "Simulation params|false;Refiner")
		@ParamInfo(name = "pre-refinements", style = "default", options = "value=0") Integer num_prerefs,
		/// Problem definition ///
		// funtions and subsets
		@ParamGroupInfo(group = "Problem definition|true;Functions|true")
		@ParamInfo(name = "functions", style = "array", options = "ugx_tag=\"gridFile\"; fct_tag=\"fctDef\"; minArraySize=1;") FunctionDefinition[] problemDefinition,
		@ParamGroupInfo(group = "Problem definition|true; Diffusion|false")
		@ParamInfo(name = "", style = "array", options = "ugx_tag=\"gridFile\"; fct_tag=\"fctDef\"; minArraySize=1; type=\"S1|mnn:function & subset, diffusion, reaction rate, reaction term\"") UserDataTuple[] diffusionData,
		@ParamGroupInfo(group = "Problem definition|true; Pl Membrane|false; VDCC")
		@ParamInfo(name = "", style = "default", options = "ugx_tag=\"gridFile\"; fct_tag=\"fctDef\"; type=\"S1|n:cytosolic calcium, density\"") UserDataTuple vdccData,
		@ParamGroupInfo(group = "Problem definition|true; NEURON setup|false; Geometry")
		@ParamInfo(name = "Load", style = "hoc-load-dialog", options = "hoc_tag=\"gridFile\"") File hocGeometry,
		@ParamGroupInfo(group = "Problem definition|true; NEURON setup|false; Stimulation")
		@ParamInfo(name = "Stimulation file", style = "load-dialog", options = "") File hocStim,
		@ParamGroupInfo(group = "Problem definition|true; Pl Membrane|false; VDCC")
		@ParamInfo(name = "channel type", style = "selection", options = "value=[\"L\",\"N\",\"T\"]") String vdccChannelType,
		@ParamGroupInfo(group = "Problem definition|true; Boundary conditions|false")
		@ParamInfo(name = "Neumann Boundary", style = "array", options = "ugx_tag=\"gridFile\"; fct_tag=\"fctDef\"; minArraySize=0; type=\"S1|n:function & subset, value\"") UserDataTuple[] bndData,
		@ParamGroupInfo(group = "Problem definition|true; Start Value|false")
		@ParamInfo(name = "Start Value", style = "array", options = "ugx_tag=\"gridFile\"; fct_tag=\"fctDef\"; minArraySize=1; type=\"S1|n:function & subset, start value\"") UserDataTuple[] startValue
	) {
		// get selected geometry and its dim
		String gridFileName = gridFile.getAbsolutePath();
		UGXFileInfo ugxFI = new UGXFileInfo();

		//  parse ugx file for world dimension
		if (ugxFI.parse_file(gridFileName) == false) {
			throw new RuntimeException("Unable to parse ugx-File: " + gridFileName);
		}

		if (ugxFI.const__num_grids() != 1) {
			throw new RuntimeException("UGX file must contain exactly one grid.");
		}

		int dim = ugxFI.const__grid_world_dimension(0);

		// Init UG for dimension and algebra
		F_InitUG.invoke(dim, new AlgebraType("CPU", 1));

		// create, load, refine and distribute domain
		System.out.println("Create, refine and distribute domain");
		String[] neededSubsets = {};
		String distributionMethod = "metisReweigh";
		I_InterSubsetPartitionWeighting weightingFct = new InterSubsetPartitionWeighting();
		weightingFct.set_default_weights(1, 1);
		weightingFct.set_inter_subset_weight(0, 1, 1000);
		I_Domain dom = createAndDistributeDomain(gridFileName, num_refs, num_prerefs, neededSubsets, distributionMethod, true, -1, -1, weightingFct);

		// create approximation space
		System.out.println("Create approximation space");
		I_ApproximationSpace approxSpace = new ApproximationSpace(dom);

		// defining approximation space according to function definition
		for (FunctionDefinition fd : problemDefinition) {
			String subsets = "";
			if (fd.getFctData().subsetList.isEmpty()) {
				throw new RuntimeException("No subset definition for function '" + fd.getFctData().fctName + "'!");
			}

			for (String s : fd.getFctData().subsetList) {
				subsets = subsets + ", " + s;
			}
			subsets = subsets.substring(2);
			approxSpace.add_fct(fd.getFctData().fctName, "Lagrange", 1, subsets);
		}

		approxSpace.init_levels();
		approxSpace.const__print_layout_statistic();
		approxSpace.const__print_statistic();

        //////////////////////////
		// discretization setup //
		//////////////////////////
		I_DomainDiscretization domainDisc = new DomainDiscretization(approxSpace);

		// diffusion processes
		I_ConvectionDiffusionFV1[] diffusionDisc = new ConvectionDiffusionFV1[diffusionData.length];
		for (int i = 0; i < diffusionData.length; i++) {
			// get selected function and selected subsets
			UserDependentSubsetModel.FSDataType fctSsSel = (UserDependentSubsetModel.FSDataType) diffusionData[i].getData(0);
			if (fctSsSel.getSelFct().length != 1) {
				throw new RuntimeException("Diffusion process " + i + " needs exactly one function, but has " + fctSsSel.getSelFct().length + ".");
			}
			String fct = fctSsSel.getSelFct()[0];
			String[] ss = fctSsSel.getSelSs();
			String ssString = "";
			if (ss.length == 0) {
				throw new RuntimeException("No subset definition for function '" + fct + "' in diffusion process " + i + "!");
			}
			for (String s : ss) {
				ssString = ssString + ", " + s;
			}
			ssString = ssString.substring(2);

			// create elemDisc
			diffusionDisc[i] = new ConvectionDiffusionFV1();
			diffusionDisc[i].constructor(fct, ssString);

			// upwinding not needed, no convection
			I_IConvectionShapes upwind = new NoUpwind();
			diffusionDisc[i].set_upwind(upwind);

			// get parameters for diffusion / reaction
			I_CplUserMatrix diffTensor = (I_CplUserMatrix) diffusionData[i].getMatrixData(1);
			I_CplUserNumber reactionRate = (I_CplUserNumber) diffusionData[i].getNumberData(2);
			I_CplUserNumber reactionTerm = (I_CplUserNumber) diffusionData[i].getNumberData(3);

			diffusionDisc[i].set_diffusion(diffTensor);
			diffusionDisc[i].set_reaction_rate(reactionRate);
			diffusionDisc[i].set_reaction(reactionTerm);

			// add to domain discretization
			domainDisc.add(diffusionDisc[i]);
		}

		// Borg-Graham VDCC
		String[] vdccSelFcts = ((UserDependentSubsetModel.FSDataType) vdccData.getData(0)).getSelFct();
		if (vdccSelFcts.length != 1) {
			throw new RuntimeException("ER leakage mechanism needs exactly one function, but has " + vdccSelFcts.length + ".");
		}
		String vdccFcts = vdccSelFcts[0];

		String[] vdccSelSs = ((UserDependentSubsetModel.FSDataType) vdccData.getData(0)).getSelSs();
		String vdccSsString = "";
		if (vdccSelSs.length == 0) {
			throw new RuntimeException("No subset definition in ER leakage definition!");
		}
		for (String s : vdccSelSs) {
			vdccSsString = vdccSsString + ", " + s;
		}
		vdccSsString = vdccSsString.substring(2);
		I_CplUserNumber vdccDensityFct = (I_CplUserNumber) vdccData.getNumberData(1);

		// prepare NEURON interpreter
		I_Transformator trans = new Transformator();
		trans.load_geom(hocGeometry.getAbsolutePath());
		trans.load_stim(hocStim.getAbsolutePath());
	///trans.setup_hoc(0d, 1.0d, 0.01d, -75.0d);

		I_Transformator trans2 = new Transformator();
		/// prepare elem disc
		I_OneSidedBorgGrahamFV1WithVM2UGNEURON vdccDisc = new OneSidedBorgGrahamFV1WithVM2UGNEURON(vdccFcts, vdccSsString, approxSpace, trans2, "", "", "", false);

		// set appropriate channel type
		if ("L".equals(vdccChannelType)) {
			vdccDisc.set_channel_type_L();
		} else if ("N".equals(vdccChannelType)) {
			vdccDisc.set_channel_type_N();
		} else if ("T".equals(vdccChannelType)) {
			vdccDisc.set_channel_type_T();
		}

		System.err.println("Number of sections: " + trans.get_sections());
		trans.execute_hoc_stmt("dt = 0.1");
		trans.execute_hoc_stmt("tstart = 0");
		trans.execute_hoc_stmt("tstop = 1");
		trans.setup_hoc(0d, 1d, 0.1d, -75d);

		// set density function and init, then add to domainDisc
		vdccDisc.set_density_function(vdccDensityFct);
		vdccDisc.set_transformator(trans);
		trans.extract_vms(1, 3);
		I_MembranePotentialMapper mapper = new MembranePotentialMapper(trans);
		vdccDisc.set_mapper(mapper);
		/**
		 * this works: I_MembranePotentialMapper mapper = new
		 * MembranePotentialMapper(trans); System.err.println("get
		 * potential: " + mapper.get_potential(0d, 0d, 0d));
		 */
		trans.print_setup(true);
		System.err.println("hoc statements executed again");

	//trans.extract_vms(1, 3);
		///I_MembranePotentialMapper mapper = new MembranePotentialMapper(trans);
		/// recompile API first
		/// somehow this fails with invalid memory access figure out why -> at least we can remove this and dont use the timestep elem prep loop
		/**
		 * todo this is new and needs testing (next 4 lines)
		 */
		vdccDisc.init(0.0d); /// a hoc geometry must be loaded already to transformator! and the timesteps must be extracted already for the first timestep...
		System.err.println("after init");
		domainDisc.add(vdccDisc);

		// Neumann boundaries
		int i = 0;
		I_UserFluxBoundaryFV1[] neumannDisc = new UserFluxBoundaryFV1[bndData.length];

		for (UserDataTuple bnd : bndData) {
			String[] bndFct = ((UserDependentSubsetModel.FSDataType) bnd.getData(0)).getSelFct();
			if (bndFct.length != 1) {
				throw new RuntimeException("Definition of Neumann boundary condition " + i + " needs exactly one function, but has " + bndFct.length + ".");
			}

			String[] bndSelSs = ((UserDependentSubsetModel.FSDataType) bnd.getData(0)).getSelSs();
			String bndSsString = "";
			if (bndSelSs.length == 0) {
				throw new RuntimeException("No subset selected in Neumann boundary definition " + i + "!");
			}
			for (String s : bndSelSs) {
				bndSsString = bndSsString + ", " + s;
			}
			bndSsString = bndSsString.substring(2);

			Logger.getLogger(MembranePotentialMapping.class.getName()).log(Level.INFO, "bndFct: {0}", bndFct[0]);
			Logger.getLogger(MembranePotentialMapping.class.getName()).log(Level.INFO, "bndSsString: {0}", bndSsString);
			neumannDisc[i] = new UserFluxBoundaryFV1(bndFct[0], bndSsString);
			neumannDisc[i].set_flux_function((I_CplUserNumber) bnd.getNumberData(1));

			domainDisc.add(neumannDisc[i]);
			i++;
		}

        /// start value
		// check that every function has been initialized on each of its subsets
		for (FunctionDefinition fd : problemDefinition) {
            // construct list of all subset lists in initial solution definition
			// for the given function
			List<List<String>> dssll = new ArrayList<List<String>>();
			for (UserDataTuple udt : startValue) {
				if (((UserDependentSubsetModel.FSDataType) udt.getData(0)).getSelFct().length != 1) {
					throw new RuntimeException("Start value definition needs exactly one function at a time, but has "
						+ ((UserDependentSubsetModel.FSDataType) udt.getData(0)).getSelFct().length + ".");
				}
				String fct = ((UserDependentSubsetModel.FSDataType) udt.getData(0)).getSelFct()[0];

				if (fct.equals(fd.getFctData().fctName)) {
					dssll.add(fd.getFctData().subsetList);
				}
			}

            // check that each item on the definition subset list
			// is somewhere in the initial solution subset list list
			List<String> dssl = fd.getFctData().subsetList;
			for (String dss : dssl) {
				boolean found = false;
				outerSearchLoop:
				for (List<String> issl : dssll) {
					for (String iss : issl) {
						if (iss.equals(dss)) {
							found = true;
							break outerSearchLoop;
						}
					}
				}
				if (!found) {
					throw new RuntimeException("Initialization value for function '"
						+ fd.getFctData().fctName + "' on subset '" + dss + "' is missing!");
				}
			}
		}

		//HOCInterpreter interpreter = HOCInterpreter.getInstance();
		return new Object[]{domainDisc, vdccDisc, approxSpace, startValue, trans};
	}

    // The following functions are mere java equivalents of lua script functions
	// defined in ug_util.lua and domain_distribution_util.lua.
	/**
	 * @brief creates and distributes the domain
	 * @param gridName
	 * @param numRefs
	 * @param numPreRefs
	 * @param neededSubsets
	 * @param distributionMethod
	 * @param verticalInterfaces
	 * @param numTargetProcs
	 * @param distributionLevel
	 * @param wFct
	 * @return
	 */
	@SuppressWarnings("AssignmentToMethodParameter")
	private I_Domain createAndDistributeDomain(String gridName, int numRefs, int numPreRefs, String[] neededSubsets, String distributionMethod, boolean verticalInterfaces, int numTargetProcs, int distributionLevel, I_PartitionWeighting wFct) {
		// create Instance of a Domain
		I_Domain dom = new Domain();

		// load domain
		System.out.print("Loading domain " + gridName + " ... ");
		F_LoadDomain.invoke(dom, gridName);
		System.out.print("done.\n");

		// create Refiner
		if (numPreRefs > numRefs) {
			throw new RuntimeException("numPreRefs must be smaller than numRefs.");
		}

		if (numPreRefs > numRefs) {
			numPreRefs = numRefs;
		}

	// Create a refiner instance. This is a factory method
		// which automatically creates a parallel refiner if required.
		I_IRefiner refiner = null;
		if (numRefs > 0) {
			System.out.print("Refining(" + numRefs + "): ");
			refiner = F_GlobalDomainRefiner.invoke(dom);

			// performing pre-refines
			for (int i = 0; i < numPreRefs; i++) {
				refiner.refine();
				System.out.print(i + " ");
			}

			System.out.print("done.\n");
		}

		// distribute the domain to all involved processes
		if (!distributeDomain(dom, distributionMethod, verticalInterfaces, numTargetProcs, distributionLevel, wFct)) {
			throw new RuntimeException("Error while Distributing Grid.");
		}

		// perform post-refine
		for (int i = numPreRefs; i < numRefs; i++) {
			refiner.refine();
		}

	// loop all subsets and search for them
		// in the SubsetHandler of the domain
		if (neededSubsets != null) {
			if (!checkSubsets(dom, neededSubsets)) {
				throw new RuntimeException("Something wrong with required subsets. Aborting.");
			}
		}

		// return the created domain
		return dom;
	}

	/**
	 * @brief distributes the supplied domain
	 * @param dom
	 * @param partitioningMethod
	 * @param verticalInterfaces
	 * @param numTargetProcs
	 * @param distributionLevel
	 * @param wFct
	 * @return
	 */
	@SuppressWarnings("AssignmentToMethodParameter")
	private boolean distributeDomain(I_Domain dom, String partitioningMethod, boolean verticalInterfaces, int numTargetProcs, int distributionLevel, I_PartitionWeighting wFct) {
		if (F_NumProcs.invoke() == 1) {
			return true;
		}

		I_PartitionMap partitionMap = new PartitionMap();

		if (partitioningMethod == null) {
			partitioningMethod = "bisection";
		}

		if (numTargetProcs <= 0) {
			numTargetProcs = F_NumProcs.invoke();
		}

		if (distributionLevel < 0) {
			distributionLevel = dom.grid().const__num_levels() - 1;
			if (distributionLevel < 0) {
				distributionLevel = 0;
			}
		}

		if (dom.const__domain_info().const__num_elements_on_level(distributionLevel) < numTargetProcs) {
			System.out.println("\nWARNING in DistributeDomain:");
			System.out.println("    There are less elements on distributionLevel than there are target processes.");
			System.out.println("    If ug hangs during parallel execution, consider increasing numPreRefs to avoid this!");
			System.out.println("    num elements on level " + distributionLevel + ": "
				+ dom.const__domain_info().const__num_elements_on_level(distributionLevel));
			System.out.println("    num target processes: " + numTargetProcs);
			System.out.println("");
		}

		if ("bisection".equals(partitioningMethod)) {
			if (distributionLevel < dom.grid().const__num_levels() - 1) {
				System.out.println("WARNING in util.DistributeDomain: 'bisection' can currently "
					+ "only be performed on the top level. Sorry...");
			}
			partitionMapBisection(dom, partitionMap, numTargetProcs);
		} else if ("metis".equals(partitioningMethod)) {
			partitionMapMetis(dom, partitionMap, numTargetProcs, distributionLevel);
		} else if ("metisReweigh".equals(partitioningMethod)) {
			if (wFct != null) {
				partitionMapMetisReweigh(dom, partitionMap, numTargetProcs, distributionLevel, wFct);
			} else {
				System.out.println("ERROR in MembranePotentialMapping::distributeDomain: "
					+ "requested partitionMethod \"metisReweigh\", but no weightingFct given.");
				return false;
			}
		} else {
			System.out.println("ERROR in util.DistributeDomain: Unknown partitioning method.\n"
				+ "  Valid partitioning methods are: 'bisection', 'metis' and 'metisReweigh'");
			return false;
		}

		boolean success = F_DistributeDomain.invoke(dom, partitionMap, verticalInterfaces);

		return success;
	}

	/**
	 * @partion by bisection method
	 * @param dom
	 * @param partitionMapOut
	 * @param numProcs
	 */
	private void partitionMapBisection(I_Domain dom, I_PartitionMap partitionMapOut, int numProcs) {
	}

	/**
	 * @brief bisect with metis
	 * @param dom
	 * @param partitionMapOut
	 * @param numProcs
	 * @param baseLevel
	 */
	private void partitionMapMetis(I_Domain dom, I_PartitionMap partitionMapOut, int numProcs, int baseLevel) {
		if (partitionMapOut.num_target_procs() != numProcs) {
			partitionMapOut.clear();
			partitionMapOut.add_target_procs(0, numProcs);
		}
		F_PartitionDomain_MetisKWay.invoke(dom, partitionMapOut, numProcs, baseLevel, 1, 1);
	}

	/**
	 * @brief reweight with metis
	 * @param dom
	 * @param partitionMapOut
	 * @param numProcs
	 * @param baseLevel
	 * @param wFct
	 */
	private void partitionMapMetisReweigh(I_Domain dom, I_PartitionMap partitionMapOut, int numProcs, int baseLevel, I_PartitionWeighting wFct) {
		if (partitionMapOut.num_target_procs() != numProcs) {
			partitionMapOut.clear();
			partitionMapOut.add_target_procs(0, numProcs);
		}
		F_PartitionDomain_MetisKWay.invoke(dom, partitionMapOut, numProcs, baseLevel, wFct);
	}

	/**
	 * @brief check for subset availability
	 * @param dom
	 * @param neededSubsets
	 * @return
	 */
	private boolean checkSubsets(I_Domain dom, String[] neededSubsets) {
		I_MGSubsetHandler sh = dom.subset_handler();
		for (String s : neededSubsets) {
			if (sh.const__get_subset_index(s) == -1) {
				System.out.print("Domain does not contain subset '" + s + "'.");
				return false;
			}
		}
		return true;
	}
}
