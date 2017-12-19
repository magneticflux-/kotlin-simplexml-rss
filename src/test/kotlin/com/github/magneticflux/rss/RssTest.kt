package com.github.magneticflux.rss

import com.github.magneticflux.rss.SampleUtils.getSample
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.hasSize
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import org.simpleframework.xml.transform.RegistryMatcher
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.io.StringWriter
import java.net.URL
import java.util.Locale

/**
 * Created by Mitchell Skaggs on 12/18/2017.
 */
class RssTest : Spek({
    given("a simple Persister") {
        val persister = Persister(
                AnnotationStrategy(),
                RegistryMatcher().apply {
                    this.bind(DayOfWeek::class.java, DayOfWeekTransform)
                })

        on("sample RSS feed read") {
            val rssFeed = persister.read(RssFeed::class.java, getSample("sample.xml"))

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
                assertThat(rssFeed.channel.categories, hasSize(equalTo(1)))
                assertThat(rssFeed.channel.categories, hasElement(Category("www.dmoz.com", "Computers/Software/Internet/Site Management/Content Management")))
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
                        Image(URL("http://www.feedforall.com/ffalogo48x48.gif"), "FeedForAll Sample Feed", URL("http://www.feedforall.com/industry-solutions.htm"), "FeedForAll Sample Feed", 48, 48)
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
                assertThat(rssFeed.channel.skipDays, hasSize(equalTo(1)))
                assertThat(rssFeed.channel.skipDays, hasElement(DayOfWeek.MONDAY))
            }

            it("should have the correct skipHours") {
                assertThat(rssFeed.channel.skipHours, hasSize(equalTo(2)))
                assertThat(rssFeed.channel.skipHours, hasElement(0))
                assertThat(rssFeed.channel.skipHours, hasElement(1))
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
        }

        on("read-write-read") {
            val rssFeed = persister.read(RssFeed::class.java, getSample("sample.xml"))

            val rssText = StringWriter()
            persister.write(rssFeed, rssText)

            val rereadRssFeed = persister.read(RssFeed::class.java, rssText.buffer.toString())

            it("should equal original RSS feed read") {
                assertThat(rereadRssFeed, equalTo(rssFeed))
            }
        }
    }
})