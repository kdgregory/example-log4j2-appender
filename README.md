This project is an example showing the progressive development of a Log4J 2.x appender, layout, and lookup.
It exists to support [this post](https://www.kdgregory.com/index.php?page=logging.log4j2Plugins) on my website.

Each stage of development is recorded as a separate commit:

* `stage-1`  
  The basic appender, which writes output to StdOut.

* `stage-2`  
  Adds a configuration property, `repeats`, which will write the log
  message multiple times.

* `stage-3`  
  Uses a Builder object to configure the appender.

* `stage-4`  
  Creates a configuration interface that's implemented by the Builder,
  so that the appender doesn't have to unpack configuration.

* `stage-5`  
  Demonstration of the appender life-cycle, using another custom appender
  that reports each life-cycle method invocation.

* `stage-6`  
  Adds support for layouts that provide a header and footer.

* `stage-7`  
  Implements an example layout.

* `stage-8`  
  Updates the example layout to support Lookups (and use them internally).

* `stage-9`  
  Implements a custom Lookup for hostname and process ID.

* `stage-10`  
  Adds unit tests.

Each of the stage names above is a tag that you can use to check out the project at that stage.

All of the stages are buildable using Maven.
