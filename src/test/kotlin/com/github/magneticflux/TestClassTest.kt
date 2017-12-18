package com.github.magneticflux

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

/**
 * Created by Mitchell Skaggs on 12/18/2017.
 */
class TestClassTest : Spek({
    describe("a TestClass") {
        val testClass = TestClass()

        it("should return true") {
            assertThat(testClass.returnTrue(), equalTo(true))
        }
    }
})