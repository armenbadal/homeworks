
#include <ctype.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

/**/
typedef struct matrix_s {
  size_t rows, columns;
  double** elements;
} matrix_t;

/**/
matrix_t* create_matrix( size_t r, size_t c )
{
  matrix_t* mx = malloc(sizeof(matrix_t));
  mx->rows = r;
  mx->columns = c;
  
  mx->elements = calloc(mx->rows, sizeof(double*));
  for( size_t r = 0; r < mx->rows; ++r )
    mx->elements[r] = calloc(mx->columns, sizeof(double));

  return mx;
}

void destroy_matrix( matrix_t* mx )
{
  for( size_t r = 0; r < mx->rows; ++r )
    free(mx->elements[r]);
  free(mx->elements);
  free(mx);
}

/**/
void set( matrix_t* mx, size_t r, size_t c, double val )
{
  // TODO: check bounds
  mx->elements[r][c] = val;
}

/**/
double get( const matrix_t* mx, size_t r, size_t c )
{
  // TODO: check bounds
  return mx->elements[r][c];
}

/**/
double _read_double( FILE* inp )
{
  int c = fgetc(inp);
  while( !isdigit(c) )
    c = fgetc(inp);
  ungetc(c, inp);
  
  double val = 0.0;
  fscanf(inp, "%lf", &val);
  return val;
}

/**/
matrix_t* read_from( FILE* inp )
{
  while( true ) {
    int c = fgetc(inp);
    if( !isspace(c) ) {
      ungetc(c, inp);
      break;
    }
  }
    
  size_t rs = 0, cs = 0;
  fscanf(inp, "%d", &rs);
  fgetc(inp);
  fscanf(inp, "%d", &cs);

  matrix_t* mx = create_matrix(rs, cs);
  for( size_t r = 0; r < mx->rows; ++r )
    for( size_t c = 0; c < mx->columns; ++c ) {
      double vl = _read_double(inp);
      set(mx, r, c, vl);
    }

  return mx;
}

/**/
matrix_t* read_from_file( const char* path )
{
  FILE* inp = fopen(path, "r");
  matrix_t* mx = read_from(inp);
  fclose(inp);
  
  return mx;
}

/**/
void write_to( const matrix_t* mx, FILE* out )
{
  fprintf(out, "%d,%d\n", mx->rows, mx->columns);
  for( size_t r = 0; r < mx->rows; ++r ) {
    for( size_t c = 0; c < mx->columns; ++c )
      fprintf(out, "%lf ", mx->elements[r][c]);
    fprintf(out, "\n");
  }
}

/**/
void write_to_file( const matrix_t* mx, const char* path )
{
  FILE* out = fopen(path, "w");
  write_to(mx, out);
  fclose(out);
}

/**/
matrix_t* add( const matrix_t* ma, const matrix_t* mb )
{
  if( ma->rows != mb->rows || ma->columns != mb->columns )
    return NULL;

  matrix_t* res = create_matrix(ma->rows, ma->columns);
  for( size_t r = 0; r < res->rows; ++r )
    for( size_t c = 0; c < res->columns; ++c ) {
      double val = get(ma, r, c) + get(mb, r, c);
      set(res, r, c, val);
    }
    
  return res;
}

/**/
double _scalar_product_of_row_and_column( const matrix_t* ma, size_t r, const matrix_t* mb, size_t c )
{
  double prod = 0.0;
  for( size_t i = 0; i < ma->columns; ++i )
    prod += get(ma, r, i) * get(mb, i, c);
  
  return prod;
}

/**/
matrix_t* multiply( const matrix_t* ma, const matrix_t* mb )
{
  if( ma->columns != mb->rows )
    return NULL;

  matrix_t* mx = create_matrix(ma->rows, ma->columns);
  for( size_t r = 0; r < ma->rows; ++r )
    for( size_t c = 0; c < ma->columns; ++c ) {
      double sp = _scalar_product_of_row_and_column(ma, r, mb, c);
      set(mx, r, c, sp);
    }
  
  return mx;
}

/**/
int main()
{
  matrix_t* ma = read_from_file("ma.txt");
  matrix_t* mb = read_from_file("ma.txt");
  matrix_t* mc = multiply(ma, mb);
  write_to_file(mc, "mc.txt");
}


