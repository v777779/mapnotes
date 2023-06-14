package ru.vpcb.map.notes.core

import ru.vpcb.map.notes.ui.auth.Signed

interface CoreExpired {
     fun userExpired(signed: Signed):Boolean

    fun notesExpired():Boolean

    fun userSigned()

    fun userUnsigned()
}
