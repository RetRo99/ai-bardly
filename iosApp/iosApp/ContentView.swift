import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
  @UIApplicationDelegateAdaptor(AppDelegate.self)
  var appDelegate: AppDelegate
        let backDispatcher = BackDispatcherKt.BackDispatcher()

    func makeUIViewController(context: Context) -> UIViewController {
       appDelegate.presenterComponent.uiViewController(backDispatcher)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea()
    }
}
