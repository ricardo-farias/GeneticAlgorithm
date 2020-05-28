### Background
Simulating a survival simulation with a genetic learning algorithm.
This project's purpose is to display how variation in a population's ecosystem 
can affect the entire population.

### Snakes

Attributes:

- Health : Number between (0 and Infinity)
- Hunger : Number between (0 and 1)
- Vision : default value of 1
- Fear (Not Implemented)
- Strength (Not Implemented)
- Toxin (Not Implemented) (Trait obtained by mutation or by selection)

Functionality:

- Move
- Look Around
- Make Decision
- Eat
- Fight (Not Implemented)


### Classifications

#### Hunger

- Hunger <= .25 = Not Hungry
- Hunger > .25 and Hunger > .50 = Getting Hungry
- Hunger > .50 and Hunger > .75 = Hungry
- Hunger > .75 = Starving

#### Fear
- Fear <= .25 = Not Scared
- Fear > .25 and Fear > .50 = Getting Scared
- Fear > .50 and Fear > .75 = Scared
- Fear > .75 = Terrified


### Equations

Health = H
Hunger = Hu
Food Eaten = FE
Number of Surrounding Snakes = NS
Number of Moves = NM
Constant = c
Fear = F
Toxin = T
Vision = V

Attribute | Equation
----------|----------
Hunger    | Hu + c
Health    | if hunger >= 1 then (H - c)
Fitness   | ((FE * Hu * H) / NM)
Fear      | ((NS * Hu * V)/H)
Strength  | None
Fight (Defending) | None
Fight (Attacking) | None

### Tasks
- [ ] Add fear to snakes
- [ ] Add diagonal movement option
- [ ] Add Fighting
- [ ] Add Toxin
- [ ] Add Fear
- [ ] Add Strength
- [ ] Create Strength Eq.
- [ ] Add genetic algorithm components:
    - [ ] Initial population random selection
    - [ ] Fitness function
    - [ ] Selection
    - [ ] Crossover
    - [ ] Mutation