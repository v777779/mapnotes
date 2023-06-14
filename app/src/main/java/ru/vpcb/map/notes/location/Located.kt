package ru.vpcb.map.notes.location

import ru.vpcb.map.notes.model.Location

interface Located {

    val currentLocation:Location?
}