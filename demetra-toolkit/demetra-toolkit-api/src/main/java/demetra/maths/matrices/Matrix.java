/*
 * Copyright 2017 National Bank of Belgium
 * 
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved 
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and 
 * limitations under the Licence.
 */
package demetra.maths.matrices;

import demetra.data.BaseTable;
import demetra.design.Development;
import java.util.stream.DoubleStream;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import demetra.data.DoubleSeq;
import java.util.function.DoubleUnaryOperator;

/**
 *
 * @author Philippe Charles
 */
@Development(status = Development.Status.Release)
public interface Matrix extends BaseTable<Double> {

    interface Mutable extends Matrix {

        @Override
        DoubleSeq.Mutable row(@Nonnull int irow);

        @Override
        DoubleSeq.Mutable diagonal();

        @Override
        DoubleSeq.Mutable subDiagonal(int pos);

        @Override
        DoubleSeq.Mutable column(@Nonnull int icolumn);

        @Override
        default Matrix.Mutable extract(@Nonnegative final int rstart, @Nonnegative final int nr,
                @Nonnegative final int cstart, @Nonnegative final int nc) {
            return new LightMutableSubMatrix(this, rstart, nr, cstart, nc);
        }

        /**
         * Sets the <code>double</code> value at the specified row/column.
         *
         * @param row
         * @param column
         * @param value
         * @throws IndexOutOfBoundsException
         */
        void set(@Nonnegative int row, @Nonnegative int column, double value) throws IndexOutOfBoundsException;

        void apply(int row, int col, DoubleUnaryOperator fn);

        default Matrix unmodifiable() {
            return Matrix.ofInternal(toArray(), getRowsCount(), getColumnsCount());
        }

        static Matrix.Mutable ofInternal(@Nonnull double[] data, @Nonnegative int nrows, @Nonnegative int ncolumns) {
            if (data.length < nrows * ncolumns) {
                throw new IllegalArgumentException();
            }
            return new LightMutableMatrix(data, nrows, ncolumns);
        }

        static Matrix.Mutable make(@Nonnegative int nrows, @Nonnegative int ncolumns) {
            return new LightMutableMatrix(new double[nrows * ncolumns], nrows, ncolumns);
        }

        static Matrix.Mutable copyOf(@Nonnull Matrix matrix) {
            return new LightMutableMatrix(matrix.toArray(), matrix.getRowsCount(), matrix.getColumnsCount());
        }

    }

    static Matrix EMPTY = new LightMatrix(null, 0, 0);

    static Matrix ofInternal(@Nonnull double[] data, @Nonnegative int nrows, @Nonnegative int ncolumns) {
        if (data.length < nrows * ncolumns) {
            throw new IllegalArgumentException();
        }
        return new LightMatrix(data, nrows, ncolumns);
    }

    static Matrix copyOf(@Nonnull Matrix matrix) {
        return new LightMatrix(matrix.toArray(), matrix.getRowsCount(), matrix.getColumnsCount());
    }

    /**
     * Returns the <code>double</code> value at the specified row/column.
     *
     * @param row
     * @param column
     * @return
     */
    double get(@Nonnegative int row, @Nonnegative int column) throws IndexOutOfBoundsException;

    /**
     *
     * @param irow
     * @return
     */
    DoubleSeq row(@Nonnull int irow);

    DoubleSeq diagonal();

    DoubleSeq subDiagonal(int pos);

    /**
     *
     * @param icolumn
     * @return
     */
    DoubleSeq column(@Nonnull int icolumn);

    default Matrix extract(@Nonnegative final int rstart, @Nonnegative final int nr,
            @Nonnegative final int cstart, @Nonnegative final int nc) {
        return new LightSubMatrix(this, rstart, nr, cstart, nc);
    }

    /**
     * Copies the data into a given buffer
     *
     * @param buffer The buffer that will receive the data.
     * @param offset The start position in the buffer for the copy. The matrix
     * will be copied in the buffer by columns at the indexes [start,
     * start+size()[. The length of the buffer is not checked (it could be
     * larger than this array.
     */
    default void copyTo(@Nonnull double[] buffer, @Nonnegative int offset) {
        int pos = offset, nr = getRowsCount(), nc = getColumnsCount();
        for (int c = 0; c < nc; ++c) {
            column(c).copyTo(buffer, pos);
            pos += nr;
        }
    }

    /**
     * @return @see DoubleStream#toArray()
     */
    @Nonnull
    default double[] toArray() {
        double[] all = new double[size()];
        int pos = 0, nr = getRowsCount(), nc = getColumnsCount();
        for (int c = 0; c < nc; ++c) {
            column(c).copyTo(all, pos);
            pos += nr;
        }
        return all;
    }

    public static String toString(Matrix matrix, String fmt) {
        StringBuilder builder = new StringBuilder();
        if (!matrix.isEmpty()) {
            DoubleSeq row = matrix.row(0);
            builder.append(DoubleSeq.format(row, fmt));
            for (int i = 1; i < matrix.getRowsCount(); ++i) {
                builder.append(System.lineSeparator());
                row = matrix.row(i);
                builder.append(DoubleSeq.format(row, fmt));
            }
        }
        return builder.toString();
    }

    public static String format(Matrix m, String fmt) {
        StringBuilder builder = new StringBuilder();
        int nrows = m.getRowsCount();
        if (nrows > 0) {
            builder.append(DoubleSeq.format(m.row(0), fmt));
            for (int r = 1; r < nrows; ++r) {
                builder.append(System.lineSeparator());
                builder.append(DoubleSeq.format(m.row(r), fmt));
            }
        }
        return builder.toString();
    }

    public static String format(Matrix m) {
        StringBuilder builder = new StringBuilder();
        int nrows = m.getRowsCount();
        if (nrows > 0) {
            builder.append(DoubleSeq.format(m.row(0)));
            for (int r = 1; r < nrows; ++r) {
                builder.append(System.lineSeparator());
                builder.append(DoubleSeq.format(m.row(r)));
            }
        }
        return builder.toString();
    }

}
