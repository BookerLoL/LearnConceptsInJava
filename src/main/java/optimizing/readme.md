# Java Optimizations

[Java Code Optimizations](code_optimizations.md)

Other Java Optimizations - Still to do

# Notes about Optimization in general

- Never prematurely optimize code
- Always design good code first then can optimize later
- Always profile code when optimizing to find true bottlenecks
- Algorithms are often the main culprit of non-performant code
- IO operations are very expensive compared to code optimizations
  - Bottleneck examples: Database resources, reading files, etc
- **Only worry about hottest code**
  - focus on writing clean code
  - measure very carefully
    - lots of tricky things
