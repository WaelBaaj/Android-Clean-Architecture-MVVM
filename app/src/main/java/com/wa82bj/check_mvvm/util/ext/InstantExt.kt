package com.wa82bj.check_mvvm.util.ext

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

fun Instant.atVST(): ZonedDateTime {
    return atZone(ZoneId.of("VST", ZoneId.SHORT_IDS))
}