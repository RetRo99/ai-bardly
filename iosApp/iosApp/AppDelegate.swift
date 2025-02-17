import SwiftUI
import ComposeApp
import GoogleSignIn

class AppDelegate: NSObject, UIApplicationDelegate {
  public let lifecycle = LifecycleRegistryKt.LifecycleRegistry()

  private lazy var appComponent = IosApplicationComponent.companion.create()

  public lazy var presenterComponent: IosViewPresenterComponent = appComponent.componentFactory.createComponent(
    componentContext: DefaultComponentContext(lifecycle: lifecycle),
    uiViewControllerProvider: { UIApplication.topViewController()! }
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
extension UIApplication {

    private class func keyWindowCompat() -> UIWindow? {
         return UIApplication
             .shared
             .connectedScenes
             .flatMap { ($0 as? UIWindowScene)?.windows ?? [] }
             .last { $0.isKeyWindow }
     }

    class func topViewController(
        base: UIViewController? = UIApplication.keyWindowCompat()?.rootViewController
    ) -> UIViewController? {
        if let nav = base as? UINavigationController {
            return topViewController(base: nav.visibleViewController)
        }

        if let tab = base as? UITabBarController {
            let moreNavigationController = tab.moreNavigationController

            if let top = moreNavigationController.topViewController, top.view.window != nil {
                return topViewController(base: top)
            } else if let selected = tab.selectedViewController {
                return topViewController(base: selected)
            }
        }

        if let presented = base?.presentedViewController {
            return topViewController(base: presented)
        }

        return base
    }
}