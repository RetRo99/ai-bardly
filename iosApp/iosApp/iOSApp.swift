import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        KoinKt.doInitKoin(appModule: ApplicationModule().module, appDeclaration: nil)
        MainViewControllerKt.doInitFirebase()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
