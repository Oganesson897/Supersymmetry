import globals.Globals
import static globals.CarbonGlobals.*
import static globals.SinteringGlobals.*

FLOTATION = recipemap('froth_flotation')
CLARIFIER = recipemap('clarifier')
CSTR = recipemap('continuous_stirred_tank_reactor')
TBR = recipemap('trickle_bed_reactor')
FBR = recipemap('fixed_bed_reactor')
BCR = recipemap('bubble_column_reactor')
BR = recipemap('batch_reactor')
FLUIDIZEDBR = recipemap('fluidized_bed_reactor')
HT_DISTILLATION_TOWER = recipemap('high_temperature_distillation')
DISTILLERY = recipemap('distillery')
ROASTER = recipemap('roaster')
MIXER = recipemap('mixer')
DRYER = recipemap('dryer')
SIFTER = recipemap('sifter')
CENTRIFUGE = recipemap('centrifuge')
PYROLYSE = recipemap('pyrolyse_oven')
LCR = recipemap('large_chemical_reactor')
EBF = recipemap('electric_blast_furnace')
VULCANIZER = recipemap('vulcanizing_press')
ALLOY_SMELTER = recipemap('alloy_smelter')
ARC_FURNACE = recipemap('arc_furnace')
AUTOCLAVE = recipemap('autoclave')
CHEMICAL_BATH = recipemap('chemical_bath')
ASSEMBLER = recipemap('assembler')
ELECTROLYTIC_CELL = recipemap('electrolytic_cell')
REACTION_FURNACE = recipemap('reaction_furnace')
ELECTROMAGNETIC_SEPARATOR = recipemap('electromagnetic_separator')
FLUID_HEATER = recipemap('fluid_heater')
ROTARY_KILN = recipemap('rotary_kiln')
FLUID_SOLIDIFIER = recipemap('fluid_solidifier')

// Zincite Dust * 1
mods.gregtech.electric_blast_furnace.removeByInput(120, [metaitem('dustSphalerite')], [fluid('oxygen') * 3000])

// Zinc ore beneficiation
MIXER.recipeBuilder()
        .inputs(ore('dustImpureSphalerite') * 4)
        .fluidInputs(fluid('distilled_water') * 2000)
        .fluidOutputs(fluid('impure_sphalerite_slurry') * 2000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

FLOTATION.recipeBuilder()
        .fluidInputs(fluid('impure_sphalerite_slurry') * 16000)
        .notConsumable(metaitem('dustSodiumEthylXanthate'))
        .notConsumable(fluid('cresol') * 100)
        .notConsumable(fluid('soda_ash_solution') * 1000)
        .fluidOutputs(fluid('galena_slurry') * 3000)
        .fluidOutputs(fluid('unprocessed_sphalerite_slurry') * 16000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

FLOTATION.recipeBuilder()
        .fluidInputs(fluid('unprocessed_sphalerite_slurry') * 16000)
        .notConsumable(metaitem('dustPotassiumAmylXanthate'))
        .notConsumable(fluid('soda_ash_solution') * 1000)
        .fluidOutputs(fluid('sphalerite_slurry') * 16000)
        .outputs(metaitem('dustGraniteTailings') * 16)
        .EUt(480)
        .duration(200)
        .buildAndRegister()

CLARIFIER.recipeBuilder()
        .fluidInputs(fluid('sphalerite_slurry') * 16000)
        .fluidOutputs(fluid('wastewater') * 16000)
        .outputs(metaitem('dustSphalerite') * 64)
        .EUt(30)
        .duration(20)
        .buildAndRegister()

MIXER.recipeBuilder()
        .inputs(ore('dustImpureSmithsonite') * 4)
        .fluidInputs(fluid('water') * 2000)
        .fluidOutputs(fluid('impure_smithsonite_slurry') * 2000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

FLOTATION.recipeBuilder()
        .fluidInputs(fluid('impure_smithsonite_slurry') * 16000)
        .notConsumable(metaitem('dustSodiumEthylXanthate'))
        .notConsumable(fluid('cresol') * 100)
        .notConsumable(fluid('soda_ash_solution') * 1000)
        .fluidOutputs(fluid('smithsonite_slurry') * 16000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

CLARIFIER.recipeBuilder()
        .fluidInputs(fluid('smithsonite_slurry') * 16000)
        .fluidOutputs(fluid('wastewater') * 16000)
        .outputs(metaitem('dustSmithsonite') * 64)
        .EUt(30)
        .duration(20)
        .buildAndRegister()

// Ore pretreatment to form oxide concentrate
ROASTER.recipeBuilder()
        .inputs(ore('dustSphalerite') * 1)
        .fluidInputs(fluid('oxygen') * 2000)
        .fluidOutputs(fluid('zinc_flue_gas') * 1000)
        .outputs(metaitem('dustZincite') * 2)
        .EUt(30)
        .duration(200)
        .buildAndRegister()

FLUIDIZEDBR.recipeBuilder()
        .inputs(ore('dustSphalerite') * 1)
        .fluidInputs(fluid('oxygen') * 2000)
        .fluidOutputs(fluid('zinc_flue_gas') * 1000)
        .outputs(metaitem('dustZincite') * 2)
        .EUt(120)
        .duration(20)
        .buildAndRegister()     

// Cadmium/Thallium chain for pyrometallurgy start
SIFTER.recipeBuilder()
        .notConsumable(metaitem('item_filter'))
        .fluidInputs(fluid('zinc_flue_gas') * 1000)
        .chancedOutput(metaitem('dustZincFlue'), 1000, 250)
        .fluidOutputs(fluid('sulfur_dioxide') * 1000)
        .EUt(120)
        .duration(20)
        .buildAndRegister()  

ROASTER.recipeBuilder()
        .inputs(ore('dustSmithsonite') * 1)
        .fluidOutputs(fluid('carbon_dioxide') * 1000)
        .outputs(metaitem('dustZincite') * 2)
        .EUt(30)
        .duration(200)
        .buildAndRegister()

FLUIDIZEDBR.recipeBuilder()
        .inputs(ore('dustSmithsonite') * 10)
        .fluidOutputs(fluid('carbon_dioxide') * 10000)
        .outputs(metaitem('dustZincite') * 20)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

// Conversion to metal via pyrometallurgy
for (combustible in CarbonGlobals.combustibles()) {
    ROASTER.recipeBuilder()
            .inputs(ore('dustZincite') * 2)
            .inputs(ore(combustible.name) * combustible.equivalent(1))
            .outputs(metaitem(combustible.byproduct))
            .fluidOutputs(fluid('crude_zinc') * 216)
            .fluidOutputs(fluid('carbon_monoxide') * 1000)
            .EUt(30)
            .duration(160 * combustible.duration)
            .buildAndRegister()
}

for (highPurityCombustible in CarbonGlobals.highPurityCombustibles()) {
        ROASTER.recipeBuilder()
            .inputs(ore('dustZincOxide') * 2)
            .inputs(ore(highPurityCombustible.name) * highPurityCombustible.equivalent(1))
            .outputs(metaitem('dustZinc'))
            .fluidOutputs(fluid('carbon_monoxide') * 1000)
            .EUt(30)
            .duration(160 * highPurityCombustible.duration)
            .buildAndRegister()
}

FLUID_SOLIDIFIER.recipeBuilder()
        .notConsumable(metaitem('shape.mold.ingot'))
        .fluidInputs(fluid('crude_zinc') * 216)
        .outputs(metaitem('ingotZinc'))
        .EUt(7)
        .duration(20)
        .buildAndRegister()

FLUID_SOLIDIFIER.recipeBuilder()
        .notConsumable(metaitem('shape.mold.ingot'))
        .fluidInputs(fluid('cadmium_rich_zinc') * 180)
        .outputs(metaitem('ingotZinc'))
        .EUt(7)
        .duration(20)
        .buildAndRegister()

HT_DISTILLATION_TOWER.recipeBuilder()
        .fluidInputs(fluid('crude_zinc') * 2160)
        .chancedOutput(metaitem('dustIron'), 200, 100)
        .fluidOutputs(fluid('cadmium_rich_zinc') * 2160)
        .fluidOutputs(fluid('copper') * 36)
        .fluidOutputs(fluid('lead') * 72)
        .fluidOutputs(fluid('tin') * 36)
        .EUt(120)
        .duration(300)
        .buildAndRegister()

HT_DISTILLATION_TOWER.recipeBuilder()
        .fluidInputs(fluid('cadmium_rich_zinc') * 2160)
        .fluidOutputs(fluid('zinc') * 2160)
        .fluidOutputs(fluid('cadmium') * 144)
        .EUt(480)
        .duration(300)
        .buildAndRegister()

// Hydrometallurgical method
CHEMICAL_BATH.recipeBuilder()
        .inputs(ore('dustZincite') * 2)
        .fluidInputs(fluid('sulfuric_acid') * 1000)
        .fluidOutputs(fluid('zinc_leach') * 1000)
        .outputs(metaitem('dustZincLeachResidue'))
        .EUt(30)
        .duration(160)
        .buildAndRegister()

FLUID_HEATER.recipeBuilder()
        .fluidInputs(fluid('sulfuric_acid') * 1000)
        .fluidOutputs(fluid('hot_sulfuric_acid') * 1000)
        .EUt(7)
        .duration(50)
        .buildAndRegister()

CHEMICAL_BATH.recipeBuilder()
        .inputs(ore('dustZincLeachResidue'))
        .fluidInputs(fluid('hot_sulfuric_acid') * 125)
        .fluidOutputs(fluid('hot_zinc_leach') * 125)
        .chancedOutput(metaitem('dustLeadSilicaResidue'), 500, 500)
        .EUt(30)
        .duration(160)
        .buildAndRegister()

CENTRIFUGE.recipeBuilder()
        .inputs(ore('dustLeadSilicaResidue'))
        .chancedOutput(metaitem('dustSiliconDioxide'), 6000, 0)
        .chancedOutput(metaitem('dustAnglesite'), 4000, 0)
        .EUt(30)
        .duration(160)
        .buildAndRegister()

MIXER.recipeBuilder()
        .inputs(ore('dustSphalerite') * 2)
        .fluidInputs(fluid('hot_zinc_leach') * 16000)
        .outputs(metaitem('dustSulfur') * 2)
        .fluidOutputs(fluid('reduced_zinc_leach') * 16000)
        .EUt(30)
        .duration(400)
        .buildAndRegister()

AUTOCLAVE.recipeBuilder()
        .fluidInputs(fluid('oxygen') * 1000)
        .fluidInputs(fluid('reduced_zinc_leach') * 16000)
        .fluidOutputs(fluid('zinc_leach') * 16000)
        .outputs(metaitem('dustIronIiiOxide') * 5)
        .EUt(30)
        .duration(400)
        .buildAndRegister()

MIXER.recipeBuilder()
        .fluidInputs(fluid('distilled_water') * 1000)
        .inputs(ore('dustZinc') * 1)
        .fluidOutputs(fluid('zinc_cementation_slurry') * 1000)
        .EUt(30)
        .duration(160)
        .buildAndRegister()

MIXER.recipeBuilder()
        .fluidInputs(fluid('zinc_leach') * 1000)
        .fluidInputs(fluid('zinc_cementation_slurry') * 100)
        .fluidOutputs(fluid('precipitated_zinc_leach') * 1000)
        .outputs(metaitem('dustCopperCadmiumResidue') * 1)
        .EUt(30)
        .duration(160)
        .buildAndRegister()

FLUID_HEATER.recipeBuilder()
        .fluidInputs(fluid('precipitated_zinc_leach') * 1000)
        .fluidOutputs(fluid('hot_precipitated_zinc_leach') * 1000)
        .EUt(30)
        .duration(100)
        .buildAndRegister()

MIXER.recipeBuilder()
        .fluidInputs(fluid('hot_precipitated_zinc_leach') * 1000)
        .fluidInputs(fluid('zinc_cementation_slurry') * 40)
        .fluidOutputs(fluid('reprecipitated_zinc_leach') * 1000)
        .outputs(metaitem('dustCobaltResidue') * 1)
        .EUt(30)
        .duration(160)
        .buildAndRegister()

CENTRIFUGE.recipeBuilder()
        .fluidInputs(fluid('sulfuric_acid') * 1000)
        .inputs(ore('dustCobaltResidue') * 20)
        .fluidOutputs(fluid('zinc_leach') * 1000)
        .outputs(metaitem('dustCobalt') * 1)
        .EUt(30)
        .duration(160)
        .buildAndRegister()

ELECTROLYTIC_CELL.recipeBuilder()
        .notConsumable(metaitem('plateLead') * 4)
        .notConsumable(metaitem('plateAluminium') * 4)
        .circuitMeta(1)
        .fluidInputs(fluid('sulfuric_acid') * 50)
        .fluidInputs(fluid('distilled_water') * 50)
        .fluidInputs(fluid('reprecipitated_zinc_leach') * 1000)
        .outputs(metaitem('dustZinc'))
        .chancedOutput(metaitem('dustZinc'), 2500, 500)
        .fluidOutputs(fluid('hydrogen') * 100)
        .fluidOutputs(fluid('oxygen') * 1050)
        .fluidOutputs(fluid('sulfuric_acid') * 1050)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

ELECTROLYTIC_CELL.recipeBuilder()
        .notConsumable(metaitem('plateLead') * 4)
        .notConsumable(metaitem('plateAluminium') * 4)
        .circuitMeta(2)
        .fluidInputs(fluid('sulfuric_acid') * 50)
        .fluidInputs(fluid('distilled_water') * 50)
        .fluidInputs(fluid('reprecipitated_zinc_leach') * 1000)
        .outputs(metaitem('dustHighPurityZinc'))
        .fluidOutputs(fluid('hydrogen') * 100)
        .fluidOutputs(fluid('oxygen') * 1050)
        .fluidOutputs(fluid('sulfuric_acid') * 1050)
        .EUt(120)
        .duration(200)
        .buildAndRegister()

// From lead processing
SIFTER.recipeBuilder()
        .notConsumable(metaitem('item_filter'))
        .fluidInputs(fluid('oxide_fume_gas') * 1000)
        .chancedOutput(metaitem('dustOxideFume'), 1000, 0)
        .fluidOutputs(fluid('carbon_monoxide') * 1000)
        .EUt(120)
        .duration(160)
        .buildAndRegister()

for (highPurityCombustible in CarbonGlobals.highPurityCombustibles()) {
        for (fuel in rotary_kiln_fuels) {
                for (comburent in rotary_kiln_comburents) {
                        ROTARY_KILN.recipeBuilder()
                                .inputs(ore('dustOxideFume') * 2)
                                .inputs(ore(highPurityCombustible.name) * highPurityCombustible.equivalent(1))
                                .outputs(metaitem('dustWaelzOxide'))
                                .outputs(metaitem('dustWaelzSlag'))
                                .fluidInputs(fluid(fuel.name) * fuel.amountRequired)
                                .fluidInputs(fluid(comburent.name) * comburent.amountRequired)
                                .fluidOutputs(fluid(fuel.byproduct) * fuel.byproductAmount)
                                .duration(fuel.duration + comburent.duration)
                                .EUt(120)
                                .buildAndRegister()
                }
        }
}



CENTRIFUGE.recipeBuilder()
        .inputs(ore('dustWaelzSlag'))
        .chancedOutput(metaitem('dustHematite'), 5000, 0)
        .chancedOutput(metaitem('dustQuicklime') * 2, 2500, 0)
        .chancedOutput(metaitem('dustSiliconDioxide') * 3, 2500, 0)
        .EUt(30)
        .duration(40)
        .buildAndRegister()