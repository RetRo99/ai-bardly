import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
  @UIApplicationDelegateAdaptor(AppDelegate.self)
  var appDelegate: AppDelegate

    func makeUIViewController(context: Context) -> UIViewController {
       appDelegate.presenterComponent.rootViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.all)
    }
}
