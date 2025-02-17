import SwiftUI
import ComposeApp
import GoogleSignIn

class AppDelegate: NSObject, UIApplicationDelegate {
  private lazy var appComponent = IosApplicationComponent.companion.create()
  private var stateKeeper = StateKeeperDispatcherKt.StateKeeperDispatcher(savedState: nil)

  public lazy var presenterComponent: IosViewPresenterComponent = appComponent.componentFactory.createComponent(
    componentContext:  DefaultComponentContext(
                                  lifecycle: ApplicationLifecycle(),
                                  stateKeeper: stateKeeper,
                                  instanceKeeper: nil,
                                  backHandler: appComponent.backDispatcher
                              )
  )

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

    func application(_ application: UIApplication, shouldSaveSecureApplicationState coder: NSCoder) -> Bool {
        appComponent.stateKeeperSaveState(coder, stateKeeper.save())
        return true
    }

    func application(_ application: UIApplication, shouldRestoreSecureApplicationState coder: NSCoder) -> Bool {
        stateKeeper = appComponent.stateKeeperRestoreState(coder)
        return true
    }

}