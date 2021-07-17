package com.hfad.todomaker

import io.realm.RealmObject

open class Task(var title: String = "", var isComplete: Boolean = false): RealmObject() {

}