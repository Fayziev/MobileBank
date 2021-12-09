package uz.gita.bank2.domain.usecase

interface SecurityScreenUseCase {
    fun getPinCode():String
    fun setPinCode(pinCode:String)
    fun setPinStatus(pinStatus:Int)
    fun getPinStatus():Int
}