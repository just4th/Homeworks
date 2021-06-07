map_get(node([], [(K, V)], _, _), K, V).

map_get(node([S1, S2], [(K1, V1)], _, _), K, V) :- K1 < K, !, map_get(S2, K, V).
map_get(node([S1, S2], [(K1, V1)], _, _), K, V) :- map_get(S1, K, V).

map_get(node([S1, S2, S3], [(K1, V1), (K2, V2)], _, _), K, V) :- K2 < K, !, map_get(S3, K, V).
map_get(node([S1, S2, S3], [(K1, V1), (K2, V2)], _, _), K, V) :- K1 < K, !, map_get(S2, K, V).
map_get(node([S1, S2, S3], [(K1, V1), (K2, V2)], _, _), K, V) :- map_get(S1, K, V).

get_max(node(_, _, MAX, _), MAX).
get_sz(node(_, _, _, SZ), SZ).


mrg([node([], [], (-1, -1), 0)|T1], B, C, T2) :- !, mrg(T1, B, C, T2).
mrg([H|T1], B, C, [H|T2]) :- mrg(T1, B, C, T2).

mrg([], [node([], [], (-1, -1), 0)|T1], C, T2) :- !, mrg([], T1, C, T2).
mrg([], [H|T1], C, [H|T2]) :- mrg([], T1, C, T2).

mrg([], [], [node([], [], (-1, -1), 0)|T1], T1) :- !.
mrg([], [], [H|T1], [H|T1]).
mrg([], [], [], []).

get_nodes([S1, S2, S3, S4], R) :- !, get_nodes([S1, S2], [A]), get_nodes([S3, S4], [B]),
																	R = [A, B].
get_nodes([S1, S2, S3], R) :- !, get_max(S1, (K1, V1)), get_max(S2, (K2, V2)), get_max(S3, (K3, V3)),
															get_sz(S1, SZ1), get_sz(S2, SZ2), get_sz(S3, SZ3), SZ is SZ1 + SZ2 + SZ3,
															R = [node([S1, S2, S3], [(K1, V1), (K2, V2)], (K3, V3), SZ)].
get_nodes([S1, S2], R) :- !, get_max(S1, (K1, V1)), get_max(S2, (K2, V2)),
													get_sz(S1, SZ1), get_sz(S2, SZ2), SZ is SZ1 + SZ2,
													R = [node([S1, S2], [(K1, V1)], (K2, V2), SZ)].
get_nodes([S1], [S1]).
get_nodes([], []).

map_put_(node([S1, S2], [(K1, V1)], _, _), K, V, R) :- K1 < K, !, map_put_(S2, K, V, S3), mrg([S1], S3, [], S), get_nodes(S, R).
map_put_(node([S1, S2], [(K1, V1)], _, _), K, V, R) :- map_put_(S1, K, V, S3), mrg(S3, [S2], [], S), get_nodes(S, R).

map_put_(node([S1, S2, S3], [(K1, V1), (K2, V2)], _, _), K, V, R) :- K2 < K, !, map_put_(S3, K, V, S4), mrg([S1, S2], S4, [], S), get_nodes(S, R).
map_put_(node([S1, S2, S3], [(K1, V1), (K2, V2)], _, _), K, V, R) :- K1 < K, !, map_put_(S2, K, V, S4), mrg([S1], S4, [S3], S), get_nodes(S, R).
map_put_(node([S1, S2, S3], [(K1, V1), (K2, V2)], _, _), K, V, R) :- map_put_(S1, K, V, S4), mrg(S4, [S2, S3], [], S), get_nodes(S, R).

map_put_(node([], [(K, V1)], _, _), K, V2, [node([], [(K, V2)], (K, V2), 1)]) :- !. 
map_put_(node([], [(K1, V1)], _, _), K2, V2, [node([], [(K1, V1)], (K1, V1), 1), node([], [(K2, V2)], (K2, V2), 1)]) :- K2 > K1, !.
map_put_(node([], [(K1, V1)], _, _), K2, V2, [node([], [(K2, V2)], (K2, V2), 1), node([], [(K1, V1)], (K1, V1), 1)]).
map_put_(node([], [], (-1, -1), 0), K, V, [node([], [(K, V)], (K, V), 1)]).

map_put(T, K, V, R) :- map_put_(T, K, V, R1), length(R1, N), N > 1, !, get_nodes(R1, [R]).
map_put(T, K, V, R) :- map_put_(T, K, V, [R]).

map_build([], node([], [], (-1, -1), 0)).
map_build([(K, V)|T], R) :- map_build(T, R1), map_put(R1, K, V, R).

last(node([], [(K, V)], _, _)).

get_children(node(R, _, _, _), R).

map_remove_(node([], [], (-1, -1), 0), _, [node([], [], (-1, -1), 0)]) :- !.
map_remove_(node([], [(K, _)], _, _), K, [node([], [], (-1, -1), 0)]) :- !.
map_remove_(node([], [(K1, V1)], _, _), K2, [node([], [(K1, V1)], (K1, V1), 1)]).


map_remove_(node([S1, S2], [(K1, _)], _, _), K, R) :- K1 < K, map_remove_(S2, K, R1), length(R1, 1), \+ last(S1), !, get_children(S1, C), mrg(C, R1, [], R).
map_remove_(node([S1, S2], [(K1, _)], _, _), K, R) :- K1 < K, map_remove_(S2, K, R1), !, get_nodes(R1, S2_), mrg([S1], S2_, [], R).

map_remove_(node([S1, S2], [(K1, _)], _, _), K, R) :- map_remove_(S1, K, R1), length(R1, 1), \+ last(S2), !, get_children(S2, C), mrg(R1, C, [], R).
map_remove_(node([S1, S2], [(K1, _)], _, _), K, R) :- map_remove_(S1, K, R1), !, get_nodes(R1, S1_), mrg(S1_, [S2], [], R).


map_remove_(node([S1, S2, S3], [(K1, _), (K2, _)], _, _), K, R) :- K2 < K, map_remove_(S3, K, R1), length(R1, 1), \+ last(S2), !, get_children(S2, C), mrg(C, R1, [], S2V), get_nodes(S2V, S2_), mrg([S1], S2_, [], R).
map_remove_(node([S1, S2, S3], [(K1, _), (K2, _)], _, _), K, R) :- K2 < K, !, map_remove_(S3, K, R1), get_nodes(R1, S3_), mrg([S1, S2], S3_, [], R).

map_remove_(node([S1, S2, S3], [(K1, _), (K2, _)], _, _), K, R) :- K1 < K, map_remove_(S2, K, R1), length(R1, 1), \+ last(S1), !, get_children(S1, C), mrg(C, R1, [], S1V), get_nodes(S1V, S1_), mrg(S1_, [S3], [], R).
map_remove_(node([S1, S2, S3], [(K1, _), (K2, _)], _, _), K, R) :- K1 < K, !, map_remove_(S2, K, R1), get_nodes(R1, S2_), mrg([S1], S2_, [S3], R).

map_remove_(node([S1, S2, S3], [(K1, _), (K2, _)], _, _), K, R) :- map_remove_(S1, K, R1), length(R1, 1), \+ last(S2), !, get_children(S2, C), mrg(R1, C, [], S2V), get_nodes(S2V, S2_), mrg(S2_, [S3], [], R).
map_remove_(node([S1, S2, S3], [(K1, _), (K2, _)], _, _), K, R) :- map_remove_(S1, K, R1), get_nodes(R1, S1_), mrg(S1_, [S2, S3], [], R).

map_remove(T, K, R) :- map_remove_(T, K, R1), get_nodes(R1, R2), get_nodes(R2, [R]).

map_headMapSize(node([], [], (-1, -1), 0), _, 0).
map_headMapSize(node([], [(K1, V)], _, _), K, 1) :- K > K1, !.
map_headMapSize(node([], [(K1, V)], _, _), K, 0).

map_headMapSize(node([S1, S2], [(K1, V1)], _, _), K, SZ) :- K1 < K, !, map_headMapSize(S2, K, SZ2), get_sz(S1, SZ1), SZ is SZ1 + SZ2.
map_headMapSize(node([S1, S2], [(K1, V1)], _, _), K, SZ) :- map_headMapSize(S1, K, SZ).

map_headMapSize(node([S1, S2, S3], [(K1, V1), (K2, V2)], _, _), K, SZ) :- K2 < K, !, map_headMapSize(S3, K, SZ3), get_sz(S1, SZ1), get_sz(S2, SZ2), SZ is SZ1 + SZ2 + SZ3.
map_headMapSize(node([S1, S2, S3], [(K1, V1), (K2, V2)], _, _), K, SZ) :- K1 < K, !, map_headMapSize(S2, K, SZ2), get_sz(S1, SZ1), SZ is SZ1 + SZ2.
map_headMapSize(node([S1, S2, S3], [(K1, V1), (K2, V2)], _, _), K, SZ) :- map_headMapSize(S1, K, SZ).

map_tailMapSize(T, K, SZ) :- get_sz(T, SZ1), map_headMapSize(T, K, SZ2), SZ is SZ1 - SZ2.
