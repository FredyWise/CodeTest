package com.fredy.mysavingsscreens.Data.Database.Event

sealed interface UserEvent {
    object SaveUser:UserEvent
}