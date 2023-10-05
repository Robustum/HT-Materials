package hiiragi283.material.core

import hiiragi283.material.api.MaterialEntryPoint
import hiiragi283.material.api.ShapeEntryPoint
import hiiragi283.material.init.HiiragiMaterials
import hiiragi283.material.init.HiiragiShapes
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint

object RagiMaterialsCore : PreLaunchEntrypoint, ShapeEntryPoint, MaterialEntryPoint {

    override fun onPreLaunch() {

    }

    override fun onShapeRegister() {
        HiiragiShapes.register()
    }

    override fun onMaterialRegister() {
        HiiragiMaterials.register()
    }

}