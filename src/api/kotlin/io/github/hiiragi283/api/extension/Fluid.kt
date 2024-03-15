@file:Suppress("UnstableApiUsage")

package io.github.hiiragi283.api.extension

import io.github.hiiragi283.api.HTMaterialsAPI
import io.github.hiiragi283.api.material.HTMaterialKey
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRenderHandler
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.block.Block
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids

private val EMPTY_RENDER_HANDLER = FluidRenderHandler { _, _, _ -> arrayOf() }

val Fluid.renderer: FluidRenderHandler
    get() = FluidRenderHandlerRegistry.INSTANCE.get(this) ?: EMPTY_RENDER_HANDLER

val Fluid.variantRenderer: FluidVariantRenderHandler
    get() = FluidVariantRendering.getHandlerOrDefault(this)

val FluidVariant.renderer: FluidVariantRenderHandler
    get() = this.fluid.variantRenderer

val Fluid.materialKey: HTMaterialKey?
    get() = HTMaterialsAPI.INSTANCE.fluidManager[this]

val Fluid.asBlock: Block
    get() = this.defaultState.blockState.block

val Fluid.notEmptyOrNull: Fluid?
    get() = takeUnless { this == Fluids.EMPTY }

val Fluid.isFlowable: Boolean
    get() = this is FlowableFluid

val Fluid.asFlowableOrNull: FlowableFluid?
    get() = this as? FlowableFluid
