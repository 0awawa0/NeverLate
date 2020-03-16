package com.awawa.neverlate

import com.awawa.neverlate.utils.minutesToTime
import com.awawa.neverlate.utils.timeToMinutes
import org.junit.Test

class Tests {

    @Test
    fun timeConverterTest() {
        for (i in 0..1440) {
            val time = minutesToTime(i)
            val mins = timeToMinutes(time)
            assert(mins == i)
        }
    }
}