package hiiragi283.material.api

interface MaterialEntryPoint {

    fun onMaterialRegister()

    companion object {
        const val NAME: String = "hiiragi283.material"
    }

}