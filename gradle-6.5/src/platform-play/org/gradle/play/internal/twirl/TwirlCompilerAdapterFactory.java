/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.play.internal.twirl;

import org.gradle.play.internal.platform.PlayMajorVersion;
import org.gradle.play.platform.PlayPlatform;

public class TwirlCompilerAdapterFactory {

    public static VersionedTwirlCompilerAdapter createAdapter(PlayPlatform playPlatform) {
        String playVersion = playPlatform.getPlayVersion();
        String scalaCompatibilityVersion = playPlatform.getScalaPlatform().getScalaCompatibilityVersion();
        VersionedPlayTwirlAdapter playTwirlAdapter = createPlayTwirlAdapter(playPlatform);
        switch (PlayMajorVersion.forPlatform(playPlatform)) {
            case PLAY_2_3_X:
                return new TwirlCompilerAdapterV10X("1.0.4", scalaCompatibilityVersion, playTwirlAdapter);
            case PLAY_2_4_X:
            case PLAY_2_5_X:
                return new TwirlCompilerAdapterV10X("1.1.1", scalaCompatibilityVersion, playTwirlAdapter);
            case PLAY_2_6_X:
                return new TwirlCompilerAdapterV13X("1.3.13", scalaCompatibilityVersion, playTwirlAdapter);
            default:
                throw new RuntimeException("Could not create Twirl compile spec for Play version: " + playVersion);
        }
    }

    public static VersionedPlayTwirlAdapter createPlayTwirlAdapter(PlayPlatform playPlatform) {
        String playVersion = playPlatform.getPlayVersion();
        switch (PlayMajorVersion.forPlatform(playPlatform)) {
            case PLAY_2_3_X:
            case PLAY_2_4_X:
            case PLAY_2_5_X:
                return new PlayTwirlAdapterV23X();
            case PLAY_2_6_X:
                return new PlayTwirlAdapterV26X();
            default:
                throw new RuntimeException("Could not create Twirl adapter spec for Play version: " + playVersion);
        }
    }

}
