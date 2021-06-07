prime(X) :- \+ composite(X).

mark_composite(I, N, PRIME) :- I > N, !.
mark_composite(I, N, PRIME) :- assert(composite(I)), J is I + PRIME, mark_composite(J, N, PRIME).

find_prime(I, N) :- I * I > N, !.
find_prime(I, N) :- composite(I), !, J is I + 1, find_prime(J, N).
find_prime(I, N) :- J is I * I, mark_composite(J, N, I), K is I + 1, find_prime(K, N).


init(MAX_N) :- find_prime(2, MAX_N).

empty([]) :- !.

get_divisors(_, 1, []) :- !.
get_divisors(H, H, [H]) :- !, prime(H).
get_divisors(H, Num, [H|T]) :- number(Num), prime(H), 0 is mod(Num, H), !, Num1 is div(Num, H), get_divisors(H, Num1, T).
get_divisors(D, Num, [H|T]) :- number(Num), D * D < Num, !, NextD is D + 1, get_divisors(NextD, Num, [H|T]).
get_divisors(D, Num, [H|T]) :- number(Num), !, H is Num, prime(Num), empty(T).

get_divisors(H, Num, [H|T]) :- !, prime(H), get_divisors(H, Num1, T), Num is Num1 * H.
get_divisors(D, Num, [H|T]) :- D < H, get_divisors(H, Num, [H|T]).

prime_index_table(2, 1).

prime_divisors(N, R) :- get_divisors(2, N, R). 

prime_index(P, N) :- number(P), !, prime(P), get_prime_index(P, N).
prime_index(P, N) :- !, get_prime_index(P, N).


get_prime_index(P, N) :- prime_index_table(P, N), !.

get_prime_index(P, N) :- number(P), prime(P), !, PrevP is P - 1, get_prime_index(PrevP, PrevN), N is PrevN + 1, assert(prime_index_table(P, N)).
get_prime_index(P, N) :- number(P), !, PrevP is P - 1, get_prime_index(PrevP, N).

next_prime(P, R) :- prime(P), !, R is P.
next_prime(P, R) :- NextP is P + 1, next_prime(NextP, R).
get_prime_index(P, N) :- number(N), PrevN is N - 1, get_prime_index(PrevP, PrevN), Prime is PrevP + 1, next_prime(Prime, P), assert(prime_index_table(P, N)).

