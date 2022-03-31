/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client;

import client.scenes.*;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AddQuoteCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AddActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdminPanelCtrl.class).in(Scopes.SINGLETON);
        binder.bind(DisplayImageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(GameScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(GameScreenMPCtrl.class).in(Scopes.SINGLETON);
        binder.bind(InBetweenScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(InstructionSceneCtrl.class).in(Scopes.SINGLETON);
        binder.bind(leaderboardSceneCtrl.class).in(Scopes.SINGLETON);
        binder.bind(StartScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(waitingRoomController.class).in(Scopes.SINGLETON);
    }
}