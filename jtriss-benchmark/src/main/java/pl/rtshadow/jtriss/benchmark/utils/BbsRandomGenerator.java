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

import java.util.Random;

// https://en.wikipedia.org/wiki/Blum_Blum_Shub
public class BbsRandomGenerator extends Random {
  final static int P = 5431, Q = 12919;
  final static int M = P * Q;

  private long seed;

  public BbsRandomGenerator(int seed) {
    if (seed < 2 || P == seed || Q == seed) {
      throw new IllegalArgumentException("Seed must be greater than 1 and different from P and Q");
    }

    this.seed = seed;
  }

  @Override
  public int nextInt() {
    seed = (seed * seed) % M;
    return (int) seed;
  }

  @Override
  protected int next(int bits) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean nextBoolean() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void nextBytes(byte[] bytes) {
    throw new UnsupportedOperationException();
  }

  @Override
  public double nextDouble() {
    throw new UnsupportedOperationException();
  }

  @Override
  public float nextFloat() {
    throw new UnsupportedOperationException();
  }

  @Override
  public synchronized double nextGaussian() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int nextInt(int n) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long nextLong() {
    throw new UnsupportedOperationException();
  }
}
