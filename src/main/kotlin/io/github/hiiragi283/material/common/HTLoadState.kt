package io.github.hiiragi283.material.common

enum class HTLoadState {
    CONSTRUCT,
    PRE_INIT,
    INIT,
    POST_INIT,
    COMPLETE;

    fun checkAfter() {
        check(this >= HTMaterialsCommon.getLoadState())
    }

    fun checkBefore() {
        check(this <= HTMaterialsCommon.getLoadState())
    }

}