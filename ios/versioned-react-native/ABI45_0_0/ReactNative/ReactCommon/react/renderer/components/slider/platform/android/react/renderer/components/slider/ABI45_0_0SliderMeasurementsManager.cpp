/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#include "ABI45_0_0SliderMeasurementsManager.h"

#include <fbjni/fbjni.h>
#include <ABI45_0_0React/ABI45_0_0jni/ReadableNativeMap.h>
#include <ABI45_0_0React/ABI45_0_0renderer/core/conversions.h>

using namespace ABI45_0_0facebook::jni;

namespace ABI45_0_0facebook {
namespace ABI45_0_0React {

Size SliderMeasurementsManager::measure(
    SurfaceId surfaceId,
    LayoutConstraints layoutConstraints) const {
  {
    std::lock_guard<std::mutex> lock(mutex_);
    if (hasBeenMeasured_) {
      return cachedMeasurement_;
    }
  }

  const jni::global_ref<jobject> &fabricUIManager =
      contextContainer_->at<jni::global_ref<jobject>>("FabricUIManager");

  static auto measure =
      jni::findClassStatic("com/facebook/ABI45_0_0React/fabric/FabricUIManager")
          ->getMethod<jlong(
              jint,
              jstring,
              ReadableMap::javaobject,
              ReadableMap::javaobject,
              ReadableMap::javaobject,
              jfloat,
              jfloat,
              jfloat,
              jfloat)>("measure");

  auto minimumSize = layoutConstraints.minimumSize;
  auto maximumSize = layoutConstraints.maximumSize;

  local_ref<JString> componentName = make_jstring("ABI45_0_0RCTSlider");

  auto measurement = yogaMeassureToSize(measure(
      fabricUIManager,
      surfaceId,
      componentName.get(),
      nullptr,
      nullptr,
      nullptr,
      minimumSize.width,
      maximumSize.width,
      minimumSize.height,
      maximumSize.height));

  // Explicitly release smart pointers to free up space faster in JNI tables
  componentName.reset();

  {
    std::lock_guard<std::mutex> lock(mutex_);
    cachedMeasurement_ = measurement;
  }

  return measurement;
}

} // namespace ABI45_0_0React
} // namespace ABI45_0_0facebook
