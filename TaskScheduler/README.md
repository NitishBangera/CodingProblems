## Scenario

Given a collection of tasks and each task takes 'duration'
to complete and may depend on other tasks. How long does the 
process take to complete all tasks. The tasks can run in parallel 
and can appear in more than one dependents array. Dependents are task
which wait for the current task to complete before themselves.

- A.dependents = [B, C]
- B.dependents = []
- C.dependents = []
- D.dependents = [C]
- E.dependents = [B, D]
- F.dependents = []