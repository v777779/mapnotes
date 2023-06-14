package ru.vpcb.map.notes.ui.auth

interface Signed {
    fun logged():Boolean
    fun logout()

    fun userExpired()
}