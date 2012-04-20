/*
// Licensed to Julian Hyde under one or more contributor license
// agreements. See the NOTICE file distributed with this work for
// additional information regarding copyright ownership.
//
// Julian Hyde licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
*/
package net.hydromatic.linq4j;

/**
 * Exposes the enumerator, which supports a simple iteration over a collection.
 *
 * <p>Analogous to LINQ's System.Collections.IEnumerable (both generic
 * and non-generic variants).</p>
 *
 * <p>Also implements {@link Iterable}, to enable use in Java foreach loops.</p>
 *
 * @param <T> element type
 */
public interface Enumerable<T> extends Iterable<T>, ExtendedEnumerable<T> {
    /**
     * Returns an enumerator that iterates through a collection.
     */
    Enumerator<T> enumerator();

    /**
     * Converts this Enumerable to a Queryable.
     *
     * @see Extensions#asQueryable(Enumerable)
     */
    Queryable<T> asQueryable();

    /**
     * Converts the elements of this Enumerable to the specified type.
     *
     * @see Extensions#cast(Enumerable, Class)
     */
    <T2> Enumerable<T2> cast(Class<T2> clazz);

}

// End Enumerable.java