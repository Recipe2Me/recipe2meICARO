frase_pregunta_recomendacion(F) :- sv(Fp), F = [que, Fp, '?'].

/*
que puedo recomendarte
que podría recomendarte
que te puedo recomendar
que te podria recomendar
que te apetece comer
que te apetece de comer
que te apeteceria comer
que te apeteceria de comer
que quieres comer
que querrias comer
que quieres de comer
que querrias de comer


que te recomiendo, que te apetece */
sv(Sv) :- es_verbo_simple(Vp, P), con_articulo(Vp), conjuga(Vp, P, V), Sv = [te, V].
/* que comes, que quieres */
sv(Sv) :- es_verbo_simple(Vp, P), \+con_articulo(Vp), conjuga(Vp, P, Sv).


sv(Sv) :- es_verbo_compuesto(Vpp, P), es_verbo_simple(Vp, P), \+es_verbo_compuesto(Vp, P), con_articulo(Vp), conjuga(Vpp, P, Vc), Sv = [te, Vc, Vp].
sv(Sv) :- es_verbo_compuesto(Vpp, P), es_verbo_simple(Vp, P), \+es_verbo_compuesto(Vp, P), con_articulo_reversible(Vp), 
			conjuga(Vpp, P, Vc), atom_concat(Vp, te, V), Sv = [Vc, V].
			
sv(Sv) :- es_verbo_compuesto(Vpp, P), es_verbo_simple(Vp, P), \+es_verbo_compuesto(Vp, P), \+con_articulo(Vp), conjuga(Vpp, P, Vc), Sv = [Vc, Vp].


es_verbo_simple(recomendar, yo).
es_verbo_simple(apetecer, tu).
es_verbo_simple(querer, tu).
es_verbo_simple(comer, tu).

es_verbo_compuesto(poder, yo).
es_verbo_compuesto(querer, tu).
es_verbo_compuesto(apetecer, tu).

con_articulo(recomendar).
con_articulo(apetecer).
con_articulo(comer).

con_articulo_reversible(recomendar).
con_articulo_reversible(comer).

conjuga(poder, yo, puedo).
conjuga(poder, yo, podria).
conjuga(querer, tu, quieres).
conjuga(querer, tu, querrias).
conjuga(recomendar, yo, recomiendo).
conjuga(apetecer, tu, apetece).
conjuga(comer, tu, comes).