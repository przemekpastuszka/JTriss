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

package pl.rtshadow.jtriss.column.accessor;

import static org.mockito.Mockito.when;

import java.util.Comparator;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractColumnAccessorTest {
  ColumnAccessorGenerator<Integer> accessorGenerator;

  @Mock
  SortedColumn<Integer> column;
  @Mock
  ColumnConstructor<Integer> constructor;

  @Before
  public void baseSetUp() {
    when(constructor.generate(Mockito.any(Comparator.class))).thenReturn(column);
  }
}
