package ru.vpcb.map.notes.core

import ru.vpcb.map.notes.ui.auth.Signed
import ru.vpcb.map.notes.utils.CryptoPrefs

object Core : CoreExpired {

    // permissions
    fun permissionState() = CryptoPrefs.getPermissionState()
    fun permissionState(state: CryptoPrefs.PermissionState) {
        CryptoPrefs.setPermissionState(state)
    }

    // user

    /**
     * force logout when login expired
     */
    override fun userExpired(signed: Signed): Boolean {
        return if (!signed.logged()) true
        else CryptoPrefs.userExpired().also { if (it) signed.logout() }
    }

    override fun notesExpired() = CryptoPrefs.expireNotes()
    override fun userSigned() = CryptoPrefs.setUser()
    override fun userUnsigned() = CryptoPrefs.resetUser()


}