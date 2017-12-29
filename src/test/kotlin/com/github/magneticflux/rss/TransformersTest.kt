package com.github.magneticflux.rss

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.throws
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZoneOffset
import org.threeten.bp.temporal.ChronoField
import java.util.Locale

class TransformersTest : Spek({
    for (dayOfWeek in DayOfWeek.values()) {

        val input = dayOfWeek.name
        given("day of week '$input'") {
            on("DayOfWeekTransform used") {

                val transform = DayOfWeekTransform.read(input)
                it("should be $input") {
                    assertThat(transform, equalTo(dayOfWeek))
                }
            }
        }
    }

    given("NotAWeekday", { "invalid day of week '$it'" }) {
        on("day of week parsed") {
            it("should throw an IllegalArgumentException") {
                assertThat({
                    DayOfWeekTransform.read(it)
                }, throws<IllegalArgumentException>())
            }
        }
    }

    given("datetime of 'Tue, 3 Jun 2008 11:05:30 GMT'") {

        val input = "Tue, 3 Jun 2008 11:05:30 GMT"
        on("ZonedDateTimeTransform used") {

            val transform = ZonedDateTimeTransform.read(input)
            it("should be in 2008") {
                assertThat(transform[ChronoField.YEAR], equalTo(2008))
            }
            it("should be in June") {
                assertThat(transform[ChronoField.MONTH_OF_YEAR], equalTo(6))
            }
            it("should be on the 3rd") {
                assertThat(transform[ChronoField.DAY_OF_MONTH], equalTo(3))
            }
            it("should be on a Tuesday") {
                assertThat(transform[ChronoField.DAY_OF_WEEK], equalTo(2))
            }
            it("should be at 11:05:30") {
                assertThat(transform[ChronoField.HOUR_OF_DAY], equalTo(11))
                assertThat(transform[ChronoField.MINUTE_OF_HOUR], equalTo(5))
                assertThat(transform[ChronoField.SECOND_OF_MINUTE], equalTo(30))
            }
            it("should be in GMT/UTC") {
                assertThat(transform.offset, equalTo(ZoneOffset.UTC))
            }
        }
    }

    given("language of 'en-us'") {
        val input = "en-us"

        on("LocaleLanguageTransform used") {
            val transform = LocaleLanguageTransform.read(input)

            it("should be US") {
                assertThat(transform, equalTo(Locale.US))
            }
        }
    }

    given("language of 'fr'") {
        val input = "fr"

        on("LocaleLanguageTransform used") {
            val transform = LocaleLanguageTransform.read(input)

            it("should be French") {
                assertThat(transform, equalTo(Locale.FRENCH))
            }
        }
    }

    given("language of 'en'") {
        val input = "en"

        on("LocaleLanguageTransform used") {
            val transform = LocaleLanguageTransform.read(input)

            it("should be English") {
                assertThat(transform, equalTo(Locale.ENGLISH))
            }
        }
    }

    given("language of 'de'") {
        val input = "de"

        on("LocaleLanguageTransform used") {
            val transform = LocaleLanguageTransform.read(input)

            it("should be German") {
                assertThat(transform, equalTo(Locale.GERMAN))
            }
        }
    }

    given("language of 'en-gb'") {
        val input = "en-gb"

        on("LocaleLanguageTransform used") {
            val transform = LocaleLanguageTransform.read(input)

            it("should be British") {
                assertThat(transform, equalTo(Locale.UK))
            }
        }
    }

    given("language of 'fr-ca'") {
        val input = "fr-ca"

        on("LocaleLanguageTransform used") {
            val transform = LocaleLanguageTransform.read(input)

            it("should be Canadian French") {
                assertThat(transform, equalTo(Locale.CANADA_FRENCH))
            }
        }
    }

    given("url of 'https://play.google.com/test/test.html'") {
        val input = "https://play.google.com/test/test.html"

        given("a read url") {
            val transform = URLTransform.read(input)

            given("a rewritten url") {
                val written = URLTransform.write(transform)
                it("should be equal to the original") {
                    assertThat(input, equalTo(written))
                }
            }

            it("should have a protocol of 'https'") {
                assertThat(transform.protocol, equalTo("https"))
            }
            it("should have a host of 'play.google.com'") {
                assertThat(transform.host, equalTo("play.google.com"))
            }
            it("should have an authority of 'play.google.com'") {
                assertThat(transform.authority, equalTo("play.google.com"))
            }
            it("should have a path of '/test/test.html'") {
                assertThat(transform.path, equalTo("/test/test.html"))
            }
            it("should have a file of 'test/test.html'") {
                assertThat(transform.path, equalTo("/test/test.html"))
            }
        }
    }

    given("10:11:12", { "duration of '$it'" }) {
        given("a parsed Duration") {
            val duration = DurationTransform.read(it)

            it("should be 36672 seconds long") {
                assertThat(duration.seconds, equalTo(36672L))
            }
        }
    }

    given("11:12", { "duration of '$it'" }) {
        given("a parsed Duration") {
            val duration = DurationTransform.read(it)

            it("should be 672 seconds long") {
                assertThat(duration.seconds, equalTo(672L))
            }
        }
    }

    given("12", { "duration of '$it'" }) {
        given("a parsed Duration") {
            val duration = DurationTransform.read(it)

            it("should be 12 seconds long") {
                assertThat(duration.seconds, equalTo(12L))
            }
        }
    }

    given("", { "duration of '$it'" }) {
        given("a parsed Duration") {
            val duration = DurationTransform.read(it)

            it("should be 0 seconds long") {
                assertThat(duration.seconds, equalTo(0L))
            }
        }
    }
})