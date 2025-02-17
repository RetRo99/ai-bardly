import SwiftUI
import ComposeApp
import GoogleSignIn

class AppDelegate: NSObject, UIApplicationDelegate {
  public let lifecycle = LifecycleRegistryKt.LifecycleRegistry()

  private lazy var appComponent = IosApplicationComponent.companion.create()

  public lazy var presenterComponent: IosViewPresenterComponent = appComponent.componentFactory.createComponent(
    componentContext: DefaultComponentContext(lifecycle: lifecycle)
  )

    public override init() {
      super.init()
      LifecycleRegistryExtKt.create(lifecycle)
    }

    deinit {
      LifecycleRegistryExtKt.destroy(lifecycle)
    }

    func application(
      _ app: UIApplication,
      open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]
    ) -> Bool {
      var handled: Bool

      handled = GIDSignIn.sharedInstance.handle(url)
      if handled {
        return true
      }

      // Handle other custom URL types.

      // If not handled by this app, return false.
      return false
    }


}