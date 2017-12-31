package com.github.magneticflux.rss

import com.github.magneticflux.rss.SampleUtils.getSample
import com.github.magneticflux.rss.itunes.ITunesSubCategory
import com.github.magneticflux.rss.itunes.ITunesTopLevelCategory
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.isA
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeDiagnosingMatcher
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.io.StringWriter
import java.net.URL
import java.util.Locale

class RssTest : Spek({
    given("a simple Persister") {
        val persister = createRssPersister()

        given("sample_ffa", { "$it RSS feed" }) {
            val rssFeed = persister.read(RssFeed::class.java, getSample("$it.xml"))

            it("should have the correct version") {
                assertThat(rssFeed.version, equalTo("2.0"))
            }

            it("should have the correct title") {
                assertThat(rssFeed.channel.title, equalTo("FeedForAll Sample Feed"))
            }

            it("should have the correct description") {
                assertThat(rssFeed.channel.description, equalTo("RSS is a fascinating technology. The uses for RSS are expanding daily. Take a closer look at how various industries are using the benefits of RSS in their businesses."))
            }

            it("should have the correct link") {
                assertThat(rssFeed.channel.link, equalTo(URL("http://www.feedforall.com/industry-solutions.htm")))
            }

            it("should have the correct categories") {
                assertThat(rssFeed.channel.categories, hasSize(1))
                assertThat(rssFeed.channel.categories, hasItem(Category("www.dmoz.com", "Computers/Software/Internet/Site Management/Content Management")))
            }

            it("should have the correct copyright") {
                assertThat(rssFeed.channel.copyright, equalTo("Copyright 2004 NotePage, Inc."))
            }

            it("should have the correct docs") {
                assertThat(rssFeed.channel.docs, equalTo(URL("http://blogs.law.harvard.edu/tech/rss")))
            }

            it("should have the correct language") {
                assertThat(rssFeed.channel.language, equalTo(Locale.US))
            }

            it("should have the correct webMaster") {
                assertThat(rssFeed.channel.webMaster, equalTo("webmaster@feedforall.com"))
            }

            it("should have the correct managingEditor") {
                assertThat(rssFeed.channel.managingEditor, equalTo("marketing@feedforall.com"))
            }

            it("should have the correct generator") {
                assertThat(rssFeed.channel.generator, equalTo("FeedForAll Beta1 (0.0.1.8)"))
            }

            it("should have the correct image") {
                assertThat(rssFeed.channel.image, equalTo(
                        Image(URL("http://www.feedforall.com/ffalogo48x48.gif"), "FeedForAll Sample Feed", URL("http://www.feedforall.com/industry-solutions.htm"), "FeedForAll Sample Feed", "48", "48")
                ))
            }

            it("should have the correct lastBuildDate") {
                assertThat(rssFeed.channel.lastBuildDate, equalTo(ZonedDateTime.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse("Tue, 19 Oct 2004 13:39:14 -0400"))))
            }

            it("should have the correct pubDate") {
                assertThat(rssFeed.channel.pubDate, equalTo(ZonedDateTime.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse("Tue, 19 Oct 2004 13:38:55 -0400"))))
            }

            it("should have the correct ttl") {
                assertThat(rssFeed.channel.ttl, equalTo(100))
            }

            it("should have the correct skipDays") {
                assertThat(rssFeed.channel.skipDays, hasSize(1))
                assertThat(rssFeed.channel.skipDays, hasItem(DayOfWeek.MONDAY))
            }

            it("should have the correct skipHours") {
                assertThat(rssFeed.channel.skipHours, hasSize(2))
                assertThat(rssFeed.channel.skipHours, hasItem(0))
                assertThat(rssFeed.channel.skipHours, hasItem(1))
            }

            it("should have the correct cloud") {
                assertThat(rssFeed.channel.cloud, equalTo(
                        Cloud("rpc.sys.com",
                                "/RPC2",
                                80,
                                "xml-rpc",
                                "myCloud.rssPleaseNotify")
                ))
            }

            it("should have the correct textInput") {
                assertThat(rssFeed.channel.textInput, equalTo(
                        TextInput("TextInput Inquiry",
                                "Your aggregator supports the textInput element. What software are you using?",
                                "query",
                                URL("http://www.cadenhead.org/textinput.php"))
                ))
            }

            it("should contain the correct items") {
                assertThat(rssFeed.channel.items, hasItem(Item(
                        "RSS Solutions for Restaurants",
                        "<b>FeedForAll </b>helps Restaurant's communicate with customers. Let your customers know the latest specials or events.<br> <br> RSS feed uses include:<br> <i><font color=\"#FF0000\">Daily Specials <br> Entertainment <br> Calendar of Events </i></font>",
                        URL("http://www.feedforall.com/restaurant.htm"),
                        listOf(Category("www.dmoz.com", "Computers/Software/Internet/Site Management/Content Management")),
                        URL("http://www.feedforall.com/forum"),
                        ZonedDateTime.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse("Tue, 19 Oct 2004 11:09:11 -0400")),
                        "lawyer@boyer.net (Lawyer Boyer)",
                        Guid(true.toString(), "http://inessential.com/2002/09/01.php#a2"),
                        Enclosure(URL("http://www.scripting.com/mp3s/weatherReportSuite.mp3"), 12216320, "audio/mpeg"),
                        Source(URL("http://www.tomalak.org/links2.xml"), "Tomalak's Realm"),
                        emptyList(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                )))
            }

            on("feed reread") {
                val rssText = StringWriter()
                persister.write(rssFeed, rssText)

                val rereadRssFeed = persister.read(RssFeed::class.java, rssText.toString())

                it("should equal original RSS feed read") {
                    assertThat(rereadRssFeed, equalTo(rssFeed))
                }

                it("should have the same hashCode as original RSS feed read") {
                    assertThat(rereadRssFeed.hashCode(), equalTo(rssFeed.hashCode()))
                }

                it("should have the same String representation as original RSS feed read") {
                    assertThat(rereadRssFeed.toString(), equalTo(rssFeed.toString()))
                }
            }

            given("sample_phs RSS feed") {
                val samplePHS = persister.read(RssFeed::class.java, getSample("sample_phs.xml"))

                it("should not be equal to sample_ffa") {
                    assertThat(samplePHS, !equalTo(rssFeed))
                }
            }
        }

        given("sample_phs RSS feed") {
            val samplePHS = persister.read(RssFeed::class.java, getSample("sample_phs.xml"))

            on("feed reread") {
                val rssText = StringWriter()
                persister.write(samplePHS, rssText)

                val rereadRssFeed = persister.read(RssFeed::class.java, rssText.buffer.toString())

                it("should equal original RSS feed read") {
                    assertThat(rereadRssFeed, equalTo(samplePHS))
                }

                it("should have the same hashCode as original RSS feed read") {
                    assertThat(rereadRssFeed.hashCode(), equalTo(samplePHS.hashCode()))
                }

                it("should have the same String representation as original RSS feed read") {
                    assertThat(rereadRssFeed.toString(), equalTo(samplePHS.toString()))
                }
            }
        }

        given("sample_podcast RSS feed") {
            val samplePodcast = persister.read(RssFeed::class.java, getSample("sample_podcast.xml"))

            on("feed reread") {
                val rssText = StringWriter()
                persister.write(samplePodcast, rssText)

                val rereadRssFeed = persister.read(RssFeed::class.java, rssText.buffer.toString())

                it("should equal original RSS feed read") {
                    assertThat(rereadRssFeed, equalTo(samplePodcast))
                }

                it("should have the same hashCode as original RSS feed read") {
                    assertThat(rereadRssFeed.hashCode(), equalTo(samplePodcast.hashCode()))
                }

                it("should have the same String representation as original RSS feed read") {
                    assertThat(rereadRssFeed.toString(), equalTo(samplePodcast.toString()))
                }
            }
        }

        given("sample_all RSS feed") {
            val sampleAll = persister.read(RssFeed::class.java, getSample("sample_all.xml"))

            on("feed reread") {
                val rssText = StringWriter()
                persister.write(sampleAll, rssText)

                val rereadRssFeed = persister.read(RssFeed::class.java, rssText.buffer.toString())

                it("should equal original RSS feed read") {
                    assertThat(rereadRssFeed, equalTo(sampleAll))
                }

                it("should have the same hashCode as original RSS feed read") {
                    assertThat(rereadRssFeed.hashCode(), equalTo(sampleAll.hashCode()))
                }

                it("should have the same String representation as original RSS feed read") {
                    assertThat(rereadRssFeed.toString(), equalTo(sampleAll.toString()))
                }
            }
        }

        given("sample_no_channel RSS feed") {
            on("RSS feed read") {
                it("should throw an exception") {
                    assertThat({
                        persister.read(RssFeed::class.java, getSample("sample_no_channel.xml"))
                    }, throws<UninitializedPropertyAccessException>())
                }
            }
        }

        given("sample_itunes RSS feed") {
            val sampleITunes = persister.read(RssFeed::class.java, getSample("sample_itunes.xml"))

            it("should have the correct itunes:category elements") {
                assertThat(sampleITunes.channel.iTunesCategories, equalTo(listOf(
                        ITunesTopLevelCategory("Technology", listOf(
                                ITunesSubCategory("Information Technology")
                        ))
                )))
            }

            on("feed reread") {
                val rssText = StringWriter()
                persister.write(sampleITunes, rssText)

                val rereadRssFeed = persister.read(RssFeed::class.java, rssText.toString())

                it("should equal original RSS feed read") {
                    assertThat(rereadRssFeed, equalTo(sampleITunes))
                }

                it("should have the same hashCode as original RSS feed read") {
                    assertThat(rereadRssFeed.hashCode(), equalTo(sampleITunes.hashCode()))
                }

                it("should have the same String representation as original RSS feed read") {
                    assertThat(rereadRssFeed.toString(), equalTo(sampleITunes.toString()))
                }
            }
        }

        given("sample_gn", { "$it RSS feed" }) {
            val rssFeed = persister.read(RssFeed::class.java, getSample("$it.xml"))

            on("feed reread") {
                val rssText = StringWriter()
                persister.write(rssFeed, rssText)

                val rereadRssFeed = persister.read(RssFeed::class.java, rssText.toString())

                it("should equal original RSS feed read") {
                    assertThat(rereadRssFeed, equalTo(rssFeed))
                }

                it("should have the same hashCode as original RSS feed read") {
                    assertThat(rereadRssFeed.hashCode(), equalTo(rssFeed.hashCode()))
                }

                it("should have the same String representation as original RSS feed read") {
                    assertThat(rereadRssFeed.toString(), equalTo(rssFeed.toString()))
                }
            }
        }

        given("sample_apple", { "$it RSS feed" }) {
            val rssFeed = persister.read(RssFeed::class.java, getSample("$it.xml"))

            on("feed reread") {
                val rssText = StringWriter()
                persister.write(rssFeed, rssText)

                val rereadRssFeed = persister.read(RssFeed::class.java, rssText.toString())

                it("should equal original RSS feed read") {
                    assertThat(rereadRssFeed, equalTo(rssFeed))
                }

                it("should have the same hashCode as original RSS feed read") {
                    assertThat(rereadRssFeed.hashCode(), equalTo(rssFeed.hashCode()))
                }

                it("should have the same String representation as original RSS feed read") {
                    assertThat(rereadRssFeed.toString(), equalTo(rssFeed.toString()))
                }
            }
        }
    }
})

inline fun <reified T : Throwable> throws(): Matcher<() -> Any?> {
    return throws(isA(T::class.java))
}

fun throws(matcher: Matcher<out Throwable>): Matcher<() -> Any?> {
    return object : TypeSafeDiagnosingMatcher<() -> Any?>() {
        override fun matchesSafely(item: (() -> Any?), mismatchDescription: Description): Boolean {
            return try {
                item()
                mismatchDescription.appendText("did not throw an exception")
                false
            } catch (e: Throwable) {
                val success = matcher.matches(e)

                if (!success) {
                    mismatchDescription.appendText("was ")
                    matcher.describeMismatch(e, mismatchDescription)
                }

                success
            }
        }

        override fun describeTo(description: Description) {
            description.appendText("a function that throws")
                    .appendDescriptionOf(matcher)
        }
    }
}

private operator fun <T> Matcher<T>.not(): Matcher<T> {
    return not(this)
}
