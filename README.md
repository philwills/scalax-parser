This is the URLParser I hacked together during my talk at [Scala Exchange 2014](https://skillsmatter.com/conferences/1948-scala-exchange-2014#program) on why you should use parser combinators rather than regular expressions.

To give it a whirl, fire up the console from SBT and run `new URLParser("http://localhost:9000/blah/bleh?foo=bar&fox=baz%20bee#frag").URL.run()`

This is in no way a fully standards compliant URL parser, but hopefully gives an idea of how a moderately complex grammar can be constructed relatively painlessly, with [Parboiled2](https://github.com/sirthias/parboiled2).
