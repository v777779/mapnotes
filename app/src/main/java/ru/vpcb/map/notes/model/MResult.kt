package ru.vpcb.map.notes.model

sealed class MResult<out T:Any?> {
    object Loading: MResult<Nothing>()
    data class Success<T:Any?>(val data:T, var message:String? = null): MResult<T>()
    data class Error(val code:String, val message:String? = null): MResult<Nothing>()
    object None: MResult<Nothing>()

}