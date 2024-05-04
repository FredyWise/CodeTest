package com.fredy.mysavingsscreens.Data.Database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fredy.mysavings.Data.Balance
import java.time.LocalDateTime

@Entity
data class Record(
    @ColumnInfo("record_id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val userIdFk: Int,
    var accountIdFromFk: Int,
    var accountIdToFk: Int,
    var categoryIdFk: Int,
    var recordDateTime: LocalDateTime = LocalDateTime.now(),
    var recordAccount: Account,
    var recordToAccount: Account? = null,
    var recordToCategory: Category? = null,
    var recordBalance: Balance = Balance(),
    var recordNotes: String = "",
)

//data class RecordsData(
//    val csvName: String = LocalDateTime.now().toString().replace(
//        "-", "_"
//    ).replace(":", ""),
//    var records: List<Record>  //this should be taken from csv file
//)
// this is imposible
//@Entity
//data class Record(
//    @ColumnInfo("record_date")
//    @PrimaryKey
//    var recordDate: LocalDate,
//    var recordItems: List<RecordItem>
//)
