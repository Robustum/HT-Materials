package io.github.hiiragi283.material

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint

@Suppress("unused")
object HTMaterialsPre : PreLaunchEntrypoint {
    override fun onPreLaunch() {
        // Collect Addons
        HTMaterialsCore.initEntryPoints()
        // Create Shapes
        HTMaterialsCore.createShape()
        // Create Materials
        HTMaterialsCore.createMaterial()
        HTMaterialsCore.verifyMaterial()
    }
}
