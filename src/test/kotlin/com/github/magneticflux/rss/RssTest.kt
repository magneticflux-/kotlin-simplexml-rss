package com.github.magneticflux.rss

import com.github.magneticflux.rss.SampleUtils.getSample
import com.github.magneticflux.rss.itunes.ITunesExplicit
import com.github.magneticflux.rss.itunes.ITunesSubCategory
import com.github.magneticflux.rss.itunes.ITunesTopLevelCategory
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.hasSize
import com.natpryce.hamkrest.throws
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

        given("sample_ffa RSS feed") {
            val sampleFFA = persister.read(RssFeed::class.java, getSample("sample_ffa.xml"))

            it("should have the correct version") {
                assertThat(sampleFFA.version, equalTo("2.0"))
            }

            it("should have the correct title") {
                assertThat(sampleFFA.channel.title, equalTo("FeedForAll Sample Feed"))
            }

            it("should have the correct description") {
                assertThat(sampleFFA.channel.description, equalTo("RSS is a fascinating technology. The uses for RSS are expanding daily. Take a closer look at how various industries are using the benefits of RSS in their businesses."))
            }

            it("should have the correct link") {
                assertThat(sampleFFA.channel.link, equalTo(URL("http://www.feedforall.com/industry-solutions.htm")))
            }

            it("should have the correct categories") {
                assertThat(sampleFFA.channel.categories, hasSize(equalTo(1)))
                assertThat(sampleFFA.channel.categories, hasElement(Category("www.dmoz.com", "Computers/Software/Internet/Site Management/Content Management")))
            }

            it("should have the correct copyright") {
                assertThat(sampleFFA.channel.copyright, equalTo("Copyright 2004 NotePage, Inc."))
            }

            it("should have the correct docs") {
                assertThat(sampleFFA.channel.docs, equalTo(URL("http://blogs.law.harvard.edu/tech/rss")))
            }

            it("should have the correct language") {
                assertThat(sampleFFA.channel.language, equalTo(Locale.US))
            }

            it("should have the correct webMaster") {
                assertThat(sampleFFA.channel.webMaster, equalTo("webmaster@feedforall.com"))
            }

            it("should have the correct managingEditor") {
                assertThat(sampleFFA.channel.managingEditor, equalTo("marketing@feedforall.com"))
            }

            it("should have the correct generator") {
                assertThat(sampleFFA.channel.generator, equalTo("FeedForAll Beta1 (0.0.1.8)"))
            }

            it("should have the correct image") {
                assertThat(sampleFFA.channel.image, equalTo(
                        Image(URL("http://www.feedforall.com/ffalogo48x48.gif"), "FeedForAll Sample Feed", URL("http://www.feedforall.com/industry-solutions.htm"), "FeedForAll Sample Feed", 48, 48)
                ))
            }

            it("should have the correct lastBuildDate") {
                assertThat(sampleFFA.channel.lastBuildDate, equalTo(ZonedDateTime.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse("Tue, 19 Oct 2004 13:39:14 -0400"))))
            }

            it("should have the correct pubDate") {
                assertThat(sampleFFA.channel.pubDate, equalTo(ZonedDateTime.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse("Tue, 19 Oct 2004 13:38:55 -0400"))))
            }

            it("should have the correct ttl") {
                assertThat(sampleFFA.channel.ttl, equalTo(100))
            }

            it("should have the correct skipDays") {
                assertThat(sampleFFA.channel.skipDays, hasSize(equalTo(1)))
                assertThat(sampleFFA.channel.skipDays, hasElement(DayOfWeek.MONDAY))
            }

            it("should have the correct skipHours") {
                assertThat(sampleFFA.channel.skipHours, hasSize(equalTo(2)))
                assertThat(sampleFFA.channel.skipHours, hasElement(0))
                assertThat(sampleFFA.channel.skipHours, hasElement(1))
            }

            it("should have the correct cloud") {
                assertThat(sampleFFA.channel.cloud, equalTo(
                        Cloud("rpc.sys.com",
                                "/RPC2",
                                80,
                                "xml-rpc",
                                "myCloud.rssPleaseNotify")
                ))
            }

            it("should have the correct textInput") {
                assertThat(sampleFFA.channel.textInput, equalTo(
                        TextInput("TextInput Inquiry",
                                "Your aggregator supports the textInput element. What software are you using?",
                                "query",
                                URL("http://www.cadenhead.org/textinput.php"))
                ))
            }

            it("should contain the correct items") {
                assertThat(sampleFFA.channel.items, hasElement(Item(
                        "RSS Solutions for Restaurants",
                        "<b>FeedForAll </b>helps Restaurant's communicate with customers. Let your customers know the latest specials or events.<br> <br> RSS feed uses include:<br> <i><font color=\"#FF0000\">Daily Specials <br> Entertainment <br> Calendar of Events </i></font>",
                        URL("http://www.feedforall.com/restaurant.htm"),
                        listOf(Category("www.dmoz.com", "Computers/Software/Internet/Site Management/Content Management")),
                        URL("http://www.feedforall.com/forum"),
                        ZonedDateTime.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse("Tue, 19 Oct 2004 11:09:11 -0400")),
                        "lawyer@boyer.net (Lawyer Boyer)",
                        Guid(true, "http://inessential.com/2002/09/01.php#a2"),
                        Enclosure(URL("http://www.scripting.com/mp3s/weatherReportSuite.mp3"), 12216320, "audio/mpeg"),
                        Source(URL("http://www.tomalak.org/links2.xml"), "Tomalak's Realm"),
                        emptyList(),
                        ITunesExplicit.NO,
                        null,
                        null
                )))
            }

            on("feed reread") {
                val rssText = StringWriter()
                persister.write(sampleFFA, rssText)

                val rereadRssFeed = persister.read(RssFeed::class.java, rssText.buffer.toString())

                it("should equal original RSS feed read") {
                    assertThat(rereadRssFeed, equalTo(sampleFFA))
                }

                it("should have the same hashCode as original RSS feed read") {
                    assertThat(rereadRssFeed.hashCode(), equalTo(sampleFFA.hashCode()))
                }

                it("should have the same String representation as original RSS feed read") {
                    assertThat(rereadRssFeed.toString(), equalTo(sampleFFA.toString()))
                }
            }

            given("sample_phs RSS feed") {
                val samplePHS = persister.read(RssFeed::class.java, getSample("sample_phs.xml"))

                it("should not be equal to sample_ffa") {
                    assertThat(samplePHS, !equalTo(sampleFFA))
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
                assertThat(sampleITunes.channel.itunesCategories, equalTo(listOf(
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
    }
})