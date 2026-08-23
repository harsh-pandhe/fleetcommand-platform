import 'package:fleetcommand_mobile/app/app.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  testWidgets('navigates between each role-root placeholder', (WidgetTester tester) async {
    await tester.pumpWidget(const App());

    expect(find.text('Hirer area'), findsOneWidget);

    for (final (String role, String route) in <(String, String)>[
      ('Owner', 'owner'),
      ('Driver', 'driver'),
      ('Admin', 'admin'),
      ('Hirer', 'hirer'),
    ]) {
      await tester.tap(find.byKey(Key('role-nav-$route')));
      await tester.pumpAndSettle();

      expect(find.text('$role area'), findsOneWidget);
    }
  });
}
