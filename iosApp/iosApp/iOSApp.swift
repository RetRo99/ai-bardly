import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        KoinKt.doInitKoin()
        MainViewControllerKt.doInitFirebase()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
