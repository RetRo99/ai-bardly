import SwiftUI
import ComposeApp
import GoogleSignIn

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    init() {
        KoinKt.doInitKoin(appModule: ApplicationModule().module, appDeclaration: nil)
        MainViewControllerKt.doInitFirebase()
    }

    // TODO missing
    //  <key>GIDServerClientID</key>
                        //<string>YOUR_SERVER_CLIENT_ID</string>
                        //
                        //<key>GIDClientID</key>
                        //<string>YOUR_IOS_CLIENT_ID</string>
                        //<key>CFBundleURLTypes</key>
                        //<array>
                        //  <dict>
                        //    <key>CFBundleURLSchemes</key>
                        //    <array>
                        //      <string>YOUR_DOT_REVERSED_IOS_CLIENT_ID</string>
                        //    </array>
                        //  </dict>
                        //</array>
    var body: some Scene {
        WindowGroup {
              ContentView().onOpenURL(perform: { url in
                  GIDSignIn.sharedInstance.handle(url)
              })
        }
     }
}
