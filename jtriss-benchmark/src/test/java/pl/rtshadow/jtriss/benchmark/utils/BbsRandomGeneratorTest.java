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

package pl.rtshadow.jtriss.benchmark.utils;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static pl.rtshadow.jtriss.benchmark.utils.BbsRandomGenerator.P;
import static pl.rtshadow.jtriss.benchmark.utils.BbsRandomGenerator.Q;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BbsRandomGeneratorTest {
  @Test
  public void throwsExceptionForForbiddenSeeds() {
    for (int forbidden : asList(P, Q, -1, 0, 1)) {
      try {
        new BbsRandomGenerator(forbidden);
      } catch (IllegalArgumentException ex) {
        continue;
      }
      fail("Expected exception for seed: " + forbidden);
    }
  }

  @Test
  public void generatesDifferentIntegers() {
    BbsRandomGenerator generator = new BbsRandomGenerator(31);

    List<Integer> randoms = new ArrayList<Integer>(25);
    for (int i = 0; i < 100; ++i) {
      randoms.add(generator.nextInt());
    }

    assertThat(randoms).doesNotHaveDuplicates();
  }

  @Test
  public void generatesSameSequencesForSameSeed() {
    BbsRandomGenerator generatorA = new BbsRandomGenerator(31);
    BbsRandomGenerator generatorB = new BbsRandomGenerator(31);

    for (int i = 0; i < 100; ++i) {
      assertThat(generatorA.nextInt()).isEqualTo(generatorB.nextInt());
    }
  }
}
