// main ewasm entry point
fun main(args: Array<String>) {

    if (eei_getCallDataSize() < 4)
        eei_revert(0, 0)

    val selector = ByteArray(4)
    eei_callDataCopy(selector, 0, 4)

    when (selector) {
        selector_balance -> do_balance()
        selector_transfer -> do_transfer()
        else -> eei_revert(0, 0)
    }
}

fun do_balance() {
    if (eei_getCallDataSize() != 24)
        eei_revert(0, 0)

    val address = ByteArray(20)
    eei_callDataCopy(address, 4, address.size)
    val balance = ByteArray(32)

    eei_storageLoad(address, balance)
    eei_finish(balance, balance.size)
}

fun do_transfer() {
    if (eei_getCallDataSize() != 32) {
        eei_revert(0, 0)
    }

    val sender = eei_getTxOrigin()
    val recipient = ByteArray(20)
    eei_callDataCopy(recipient, 4, recipient.size)

    val valueBuffer = ByteArray(8)

    eei_callDataCopy(valueBuffer, 24, 8)
    val value = valueBuffer.toI32()

    val senderBalanceArray = ByteArray(32)
    eei_storageLoad(sender, senderBalanceArray)
    var sender_balance = senderBalanceArray.toI32()


    val recipientBalanceArray = ByteArray(32)
    eei_storageLoad(recipient, recipientBalanceArray)
    var recipient_balance = recipientBalanceArray.toI32()

    if (sender_balance < value)
        eei_revert(0, 0)

    sender_balance -= value
    recipient_balance += value

    eei_storageStore(sender, sender_balance.toByteArray())
    eei_storageStore(recipient, recipient_balance.toByteArray())
}
