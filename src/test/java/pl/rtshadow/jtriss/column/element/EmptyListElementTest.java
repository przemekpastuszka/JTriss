/*
   Copyright 2012 Przemysław Pastuszka

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package pl.rtshadow.jtriss.column.element;

import static org.fest.assertions.Assertions.assertThat;
import static pl.rtshadow.jtriss.column.element.EmptyListElement.EMPTY_LIST_AWARE_CMP;
import static pl.rtshadow.jtriss.test.TestColumnElement.element;

import org.junit.Test;

public class EmptyListElementTest {
  @Test
  public void comparatorWorksFine() {
    assertThat(EMPTY_LIST_AWARE_CMP.compare(new EmptyListElement(), element(1))).isNegative();
    assertThat(EMPTY_LIST_AWARE_CMP.compare(element(1), new EmptyListElement())).isPositive();

    assertThat(EMPTY_LIST_AWARE_CMP.compare(element(0), element(1))).isNegative();
    assertThat(EMPTY_LIST_AWARE_CMP.compare(element(1), element(0))).isPositive();
    assertThat(EMPTY_LIST_AWARE_CMP.compare(element(0), element(0))).isZero();
  }
}
