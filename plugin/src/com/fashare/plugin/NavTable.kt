package com.fashare.plugin

import com.intellij.json.psi.JsonProperty
import com.intellij.psi.PsiElement

object NavTable{
    const val TAG = "NavTable: "
    val table: MutableSet<Row> = mutableSetOf()

    fun addRow(row: Row) {
        val target = table.find {
            it.bridgePsi?.name == row.bridgePsi?.name
                    && it.bridgePsi?.value == row.bridgePsi?.value
        }

        if(target == null)
            table.add(row)
        else{
            target.fill(row)
        }
    }

    data class Row(var invokePsiSet: MutableSet<PsiElement> = mutableSetOf(),
                   var bridgePsi: JsonProperty? = null,
                   var declarePsi: PsiElement? = null) {

        fun fill(other: Row) {
            invokePsiSet.addAll(other.invokePsiSet.filter {
                !MyPsiUtil.contains(invokePsiSet, it)
            })
            bridgePsi = other.bridgePsi
            declarePsi = other.declarePsi
        }
    }

    fun print(){
        table.forEach { println(TAG + it) }
        print("\n\n\n\n")
    }
}