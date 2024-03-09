import globals.Globals
import static globals.SinteringGlobals.*

BR = recipemap('batch_reactor')
ROASTER = recipemap('roaster')
CENTRIFUGE = recipemap('centrifuge')
ELECTROLYTIC_CELL = recipemap('electrolytic_cell')
REACTION_FURNACE = recipemap('reaction_furnace')
DISTILLERY = recipemap('distillery')
SINTERING_OVEN = recipemap('sintering_oven')
AUTOCLAVE = recipemap('autoclave')
FLUID_EXTRACTOR = recipemap('extractor')
ARC_FURNACE = recipemap('arc_furnace')
DT = recipemap('distillation_tower')
FLUID_SOLIDIFIER = recipemap('fluid_solidifier')

//Emerald: 3BeO · Al2O3 · 6SiO2
//Bertrandite: Be4Si2O7(OH)2

//SULFATE PROCESS (PRIMITIVE, 75%)
//ALKALI FUSION
REACTION_FURNACE.recipeBuilder()
        .inputs(ore('dustEmerald'))
        .inputs(ore('dustSodaAsh') * 6)
        .outputs(metaitem('dustAlkaliFusedBeryl') * 2)
        .EUt(120)
        .duration(100)
        .buildAndRegister()

//DISSOLUTION
BR.recipeBuilder()
        .inputs(ore('dustAlkaliFusedBeryl') * 2)
        .fluidInputs(fluid('sulfuric_acid') * 7100)
        .fluidInputs(fluid('distilled_water') * 900)
        .outputs(metaitem('dustSiliconDioxide') * 18)
        .fluidOutputs(fluid('carbon_dioxide') * 1000)
        .fluidOutputs(fluid('dissolved_beryl') * 1000)
        .EUt(120)
        .duration(100)
        .buildAndRegister()

//Na2CO3 + H2SO4 --> Na2SO4 + H2O + CO2
//3 BeO + 3 H2SO4 --> 3 BeSO4 + 3 H2O
//Al2O3 + 3 H2SO4 --> Al2SO4 + 3 H2O

//ALUM SEPARATION
BR.recipeBuilder()
        .inputs(ore('dustPotassiumSulfate') * 7)
        .fluidInputs(fluid('dissolved_beryl') * 2000)
        .outputs(metaitem('dustPotassiumAlum') * 24)
        .fluidOutputs(fluid('impure_beryllium_sulfate_solution') * 1500)
        //Beryllium, 3, Sodium, 2, Sulfur, 4, Oxygen, 16, Water, 8 + Iron/Manganese sulfate impurities
        .EUt(120)
        .duration(200)
        .buildAndRegister()

BR.recipeBuilder() //Potassium permangante is typically added to oxidize iron II to III, however this may be too demanding for an MV line
        .inputs(ore('dustTinyCalcite') * 5)
        .fluidInputs(fluid('impure_beryllium_sulfate_solution') * 1000)
        .outputs(metaitem('dustTinyIronIiiHydroxide'))
        .outputs(metaitem('dustTinyChromiumIiiHydroxide'))
        .outputs(metaitem('dustTinyGypsum') * 8)
        .fluidOutputs(fluid('beryllium_sulfate_solution') * 1000)
        .EUt(120)
        .duration(100)
        .buildAndRegister()

BR.recipeBuilder() //75%
        .inputs(ore('dustSodiumHydroxide') * 18)
        .circuitMeta(1)
        .fluidInputs(fluid('beryllium_sulfate_solution') * 1000)
        .outputs(metaitem('dustBerylliumHydroxide') * 9)
        .fluidOutputs(fluid('sodium_sulfate_solution') * 8000)
        .EUt(120)
        .duration(100)
        .buildAndRegister()

BR.recipeBuilder() //100% (Technically, it makes a little bit of ferric EDTA but I dont want to model it)
        .inputs(ore('dustSodiumHydroxide') * 18)
        .inputs(ore('dustTinyTetrasodiumEthylenediaminetetraacetate'))
        .fluidInputs(fluid('beryllium_sulfate_solution') * 1000)
        .outputs(metaitem('dustBerylliumHydroxide') * 12)
        .fluidOutputs(fluid('sodium_sulfate_solution') * 8000)
        .EUt(120)
        .duration(100)
        .buildAndRegister()

//FLUORIDE PROCESS (UNIVERSAL, 100%)
BR.recipeBuilder()
        .fluidInputs(fluid('hexafluorosilicic_acid') * 1000)
        .fluidInputs(fluid('salt_water') * 2000)
        .outputs(metaitem('dustSodiumFluorosilicate') * 9)
        .fluidOutputs(fluid('hydrochloric_acid') * 2000)
        .EUt(Globals.voltAmps[4])
        .duration(400)
        .buildAndRegister()

REACTION_FURNACE.recipeBuilder()
        .inputs(ore('dustEmerald') * 2)
        .inputs(ore('dustSodiumFluorosilicate') * 54)
        .outputs(metaitem('dustFluorideFusedBeryl') * 23)
        .fluidOutputs(fluid('silicon_tetrafluoride') * 1000)
        .EUt(Globals.voltAmps[4])
        .duration(200)
        .buildAndRegister()

//REGENERATION OF SODIUM FLUOROSILICATE
BR.recipeBuilder()
        .inputs(ore('dustSodiumFluoride') * 4)
        .fluidInputs(fluid('silicon_tetrafluoride') * 1000)
        .outputs(metaitem('dustSodiumFluorosilicate') * 9)
        .EUt(Globals.voltAmps[4])
        .duration(100)
        .buildAndRegister()

//SEPARATION OF SODIUM FLUOROBERYLLATE
CENTRIFUGE.recipeBuilder()
        .inputs(ore('dustFluorideFusedBeryl') * 23)
        .fluidInputs(fluid('distilled_water') * 6000)
        .outputs(metaitem('dustAlumina') * 10)
        .outputs(metaitem('dustSiliconDioxide') * 45)
        .fluidOutputs(fluid('sodium_fluoroberyllate_solution') * 5400)
        .EUt(Globals.voltAmps[4])
        .duration(300)
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustSodiumHydroxide') * 6)
        .fluidInputs(fluid('sodium_fluoroberyllate_solution') * 1000)
        .fluidInputs(fluid('distilled_water') * 3000)
        .outputs(metaitem('dustBerylliumHydroxide') * 5)
        .fluidOutputs(fluid('sodium_fluoride_solution') * 4000)
        .EUt(Globals.voltAmps[4])
        .duration(80)
        .buildAndRegister()

ROASTER.recipeBuilder()
        .inputs(ore('dustBerylliumHydroxide') * 5)
        .outputs(metaitem('dustBerylliumOxide') * 2)
        .fluidOutputs(fluid('steam') * 1000)
        .EUt(Globals.voltAmps[4])
        .duration(80)
        .buildAndRegister()

//CHLORIDE PROCESS (UNIVERSAL, 90%, SHORTER)
//3BeO · Al2O3 · 6SiO2 + 18Cl2 + 18C → 3BeCl2 + 2AlCl3 + 6SiCl4 + 18CO
REACTION_FURNACE.recipeBuilder()
        .inputs(ore('dustEmerald'))
        .inputs(ore('dustAnyPurityCarbon') * 18)
        .fluidInputs(fluid('chlorine') * 36000)
        .fluidOutputs(fluid('chlorinated_beryl') * 900)
        .EUt(Globals.voltAmps[4])
        .duration(480)
        .buildAndRegister()

DT.recipeBuilder() //Maybe switch to a fractional condenser
        .fluidInputs(fluid('chlorinated_beryl') * 1000)
        .outputs(metaitem('dustBerylliumChloride') * 9)
        .fluidOutputs(fluid('aluminium_chloride') * 2000)
        .fluidOutputs(fluid('silicon_tetrachloride') * 6000)
        .fluidOutputs(fluid('carbon_monoxide') * 18000)
        .EUt(Globals.voltAmps[4])
        .duration(480)
        .buildAndRegister()

FLUID_SOLIDIFIER.recipeBuilder()
        .fluidInputs(fluid('aluminium_chloride') * 1000)
        .outputs(metaitem('dustAluminiumChloride') * 4)
        .EUt(Globals.voltAmps[1])
        .duration(20)
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustAluminiumChloride') * 4)
        .circuitMeta(1)
        .fluidInputs(fluid('sodium_hydroxide_solution') * 3000)
        .outputs(metaitem('dustAluminiumHydroxide') * 7)
        .fluidOutputs(fluid('salt_water') * 3000)
        .EUt(Globals.voltAmps[1])
        .duration(480)
        .buildAndRegister()

//BERYLLIUM CHLORIDE ROUTE (SHORTER, EV)
REACTION_FURNACE.recipeBuilder()
        .inputs(ore('dustBerylliumOxide') * 2)
        .inputs(ore('dustAnyPurityCarbon') * 1)
        .fluidInputs(fluid('chlorine') * 2000)
        .outputs(metaitem('dustBerylliumChloride') * 3)
        .fluidOutputs(fluid('carbon_monoxide') * 1000)
        .EUt(Globals.voltAmps[4])
        .duration(240)
        .buildAndRegister()

ELECTROLYTIC_CELL.recipeBuilder()
        .notConsumable(metaitem('graphite_electrode'))
        .notConsumable(metaitem('stickAluminium'))
        .notConsumable(fluid('salt') * 432)
        .fluidInputs(fluid('beryllium_chloride') * 432)
        .fluidOutputs(fluid('chlorine') * 2000)
        .outputs(metaitem('dustBeryllium'))
        .EUt(Globals.voltAmps[4])
        .duration(480)
        .buildAndRegister()

//BERYLLIUM FLUORIDE ROUTE (TAKES LONGER, MV)
BR.recipeBuilder()
        .fluidInputs(fluid('hexafluorosilicic_acid') * 1000)
        .fluidInputs(fluid('ammonia') * 6000)
        .fluidInputs(fluid('distilled_water') * 2000)
        .outputs(metaitem('dustSiliconDioxide') * 3)
        .outputs(metaitem('dustAmmoniumFluoride') * 36)
        .EUt(Globals.voltAmps[2])
        .duration(100)
        .buildAndRegister()

ROASTER.recipeBuilder()
        .circuitMeta(1)
        .inputs(ore('dustAmmoniumFluoride') * 12)
        .outputs(metaitem('dustAmmoniumBifluoride') * 8)
        .fluidOutputs(fluid('ammonia') * 1000)
        .EUt(Globals.voltAmps[2])
        .duration(100)
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustAmmoniumBifluoride') * 16)
        .inputs(ore('dustBerylliumHydroxide') * 5)
        .fluidInputs(fluid('distilled_water') * 1000)
        .fluidOutputs(fluid('ammonium_fluoroberyllate_solution') * 1000)
        .EUt(Globals.voltAmps[2])
        .duration(100)
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustTinySodiumHydroxide'))
        .fluidInputs(fluid('ammonium_fluoroberyllate_solution') * 1000)
        .chancedOutput(metaitem('dustTinyChromiumIiiHydroxide'), 200, 0)
        .chancedOutput(metaitem('dustTinyIronIiiHydroxide'), 200, 0)
        .fluidOutputs(fluid('ammonium_fluoroberyllate_solution') * 1000)
        .EUt(Globals.voltAmps[2])
        .duration(100)
        .buildAndRegister()

ROASTER.recipeBuilder()
        .fluidInputs(fluid('ammonium_fluoroberyllate_solution') * 1000)
        .outputs(metaitem('dustAmmoniumFluoroberyllate') * 13)
        .fluidOutputs(fluid('steam') * 3000)
        .EUt(Globals.voltAmps[2])
        .duration(100)
        .buildAndRegister()

ROASTER.recipeBuilder()
        .inputs(ore('dustAmmoniumFluoroberyllate') * 13)
        .outputs(metaitem('dustBerylliumFluoride') * 3)
        .fluidOutputs(fluid('disassociated_ammonium_fluoride') * 2000)
        .EUt(Globals.voltAmps[2])
        .duration(100)
        .buildAndRegister()

BR.recipeBuilder()
        .fluidInputs(fluid('disassociated_ammonium_fluoride') * 1000)
        .fluidInputs(fluid('water') * 1000)
        .fluidOutputs(fluid('ammonium_fluoride_solution') * 1000)
        .EUt(Globals.voltAmps[2])
        .duration(100)
        .buildAndRegister()

DISTILLERY.recipeBuilder()
        .fluidInputs(fluid('ammonium_fluoride_solution') * 1000)
        .outputs(metaitem('dustAmmoniumFluoride') * 6)
        .fluidOutputs(fluid('water') * 1000)
        .EUt(Globals.voltAmps[2])
        .duration(100)
        .buildAndRegister()

SINTERING_OVEN.recipeBuilder()
        .inputs(ore('dustGraphite') * 7)
        .notConsumable(metaitem('shape.mold.crucible'))
        .fluidInputs(fluid('coal_tar') * 1000)
        .outputs(metaitem('crucible.graphite'))
        .duration(1440)
        .EUt(Globals.voltAmps[2])
        .buildAndRegister()

REACTION_FURNACE.recipeBuilder()
        .inputs(ore('dustBerylliumFluoride') * 3)
        .inputs(ore('dustAnyPurityMagnesium'))
        .notConsumable(metaitem('crucible.graphite'))
        .outputs(metaitem('dustBeryllium'))
        .outputs(metaitem('dustMagnesiumFluoride') * 3)
        .EUt(Globals.voltAmps[2])
        .duration(100)
        .buildAndRegister()

BR.recipeBuilder()
        .inputs(ore('dustMagnesiumFluoride') * 3)
        .fluidInputs(fluid('hydrochloric_acid') * 2000)
        .fluidOutputs(fluid('dissolved_magnesium_fluoride') * 1000)
        .EUt(Globals.voltAmps[2])
        .duration(100)
        .buildAndRegister()

DISTILLERY.recipeBuilder()
        .fluidInputs(fluid('dissolved_magnesium_fluoride') * 1000)
        .outputs(metaitem('dustMagnesiumChloride') * 3)
        .fluidOutputs(fluid('hydrofluoric_acid') * 2000)
        .EUt(Globals.voltAmps[2])
        .duration(100)
        .buildAndRegister()

//BERTRANDITE CHAIN (SPECIALIZED)

//HIGH PURITY CHAIN
