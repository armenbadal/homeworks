
CF=-fprofile-arcs -ftest-coverage
DE=-gdwarf-2

all: NamedPoint.hxx NamedPoint.cxx point_test.cxx
	clang++ --std=c++11 $(DE) $(CF) *.cxx -o point_test


test:
	./point_test
	llvm-cov gcov NamedPoint.cxx

clean:
	rm point_test
	rm *.gcov *.gcno *.gcda

