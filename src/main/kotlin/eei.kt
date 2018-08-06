// https://github.com/ewasm/design/blob/master/eth_interface.md#getcalldatasize
@SymbolName("getCallDataSize")
external fun eei_getCallDataSize(): Int

// https://github.com/ewasm/design/blob/master/eth_interface.md#calldatacopy
@SymbolName("callDataCopy")
external fun eei_callDataCopy(resultOffset: ByteArray, dataOffset: Int, length: Int)

// https://github.com/ewasm/design/blob/master/eth_interface.md#revert
@SymbolName("revert")
external fun eei_revert(dataOffset: Int, length: Int)

// https://github.com/ewasm/design/blob/master/eth_interface.md#gettxorigin
@SymbolName("getTxOrigin")
external fun eei_getTxOrigin(): ByteArray

// https://github.com/ewasm/design/blob/master/eth_interface.md#gettxorigin
@SymbolName("getCaller")
external fun eei_getCaller(): ByteArray

// https://github.com/ewasm/design/blob/master/eth_interface.md#finish
@SymbolName("finish")
external fun eei_finish(offset: ByteArray, length: Int)

// https://github.com/ewasm/design/blob/master/eth_interface.md#storageload
@SymbolName("storageLoad")
external fun eei_storageLoad(pathOffset: ByteArray, resultOffset: ByteArray)

// https://github.com/ewasm/design/blob/master/eth_interface.md#storagestore
@SymbolName("storageStore")
external fun eei_storageStore(pathOffset: ByteArray, valueOffset: ByteArray)
